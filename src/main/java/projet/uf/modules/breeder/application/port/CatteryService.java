package projet.uf.modules.breeder.application.port;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.model.CatteryDetails;
import projet.uf.modules.breeder.application.port.in.BreederUseCase;
import projet.uf.modules.breeder.application.port.in.CatteryUseCase;
import projet.uf.modules.breeder.application.port.out.CatteryPersistencePort;
import projet.uf.modules.breeder.application.port.out.CatteryUserPersistencePort;
import projet.uf.modules.breeder.application.port.out.UserAccessPort;
import projet.uf.modules.breeder.domain.model.Breeder;
import projet.uf.modules.breeder.domain.model.Cattery;
import projet.uf.modules.breeder.domain.model.CatteryUser;
import projet.uf.modules.user.domain.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public class CatteryService implements
        CatteryUseCase
{
    private final CatteryPersistencePort catteryPersistencePort;
    private final CatteryUserPersistencePort catteryUserPersistencePort;
    private final BreederUseCase breederUseCase;
    private final UserAccessPort userAccessPort;

    @Override
    public CatteryDetails create(Long createdByUserId, String name, OperatorUser operatorUser) {
        Long userId;

        // Seul un administrateur peut créer une chatterie pour un autre utilisateur
        if (createdByUserId != null) {
            if (!operatorUser.isAdmin()) {
                throw new ApiException("Seul un administrateur peut créer une chatterie pour un autre utilisateur", HttpStatus.FORBIDDEN);
            }
            userId = createdByUserId;
        // Sinon, c'est l'ID de l'opérateur qui est pris
        } else {
            userId = operatorUser.getId();
        }

        User user = userAccessPort.getById(userId)
                .orElseThrow(() -> new ApiException("Utilisateur introuvable", HttpStatus.BAD_REQUEST));

        Cattery newCattery = catteryPersistencePort.insert(new Cattery(userId));
        Breeder breeder = breederUseCase.initialize(name, newCattery.getId());
        newCattery.setLinkedToBreederId(breeder.getId());
        catteryPersistencePort.update(newCattery);

        return this.toDetails(newCattery, user, breeder);
    }

    @Override
    public CatteryDetails getById(Long catteryId, OperatorUser operator) {
        Cattery cattery = catteryPersistencePort.getById(catteryId)
                .orElseThrow(() -> new ApiException("Chatterie introuvable", HttpStatus.NOT_FOUND));

        // L'utilisateur doit être admin ou créateur/membre de la chatterie pour accéder aux données
        boolean isCreator = cattery.getCreatedByUserId().equals(operator.getId());
        boolean isMember = catteryUserPersistencePort.getByCatteryId(catteryId).stream()
                .anyMatch(cu -> cu.getUserId().equals(operator.getId()));
        if (!operator.isAdmin() && !isCreator && !isMember) {
            throw new ApiException("Accès interdit à cette chatterie", HttpStatus.FORBIDDEN);
        }

        return this.toDetails(cattery);
    }

    @Override
    public List<CatteryDetails> getAll(OperatorUser operator) {
        if (operator.isAdmin()) {
            return catteryPersistencePort.getAll().stream().map(this::toDetails).toList();
        }
        return getAllAccessibleFromUser(operator.getId());
    }

    @Override
    public void deleteById(Long id, OperatorUser operator) {
        Cattery cattery = catteryPersistencePort.getById(id)
                .orElseThrow(() -> new ApiException("Chatterie introuvable", HttpStatus.NOT_FOUND));

        boolean isCreator = cattery.getCreatedByUserId().equals(operator.getId());

        if (!operator.isAdmin() && !isCreator) {
            throw new ApiException("Seul un administrateur ou l'administrateur de la chatterie peut la supprimer", HttpStatus.FORBIDDEN);
        }

        catteryPersistencePort.deleteById(id);
    }

    // TODO : Splitter ce mastodonte
    @Override
    public void addUserToCattery(Long catteryId, Long userId, String email, OperatorUser operator) {
        // Récupère la chatterie
        Cattery cattery = catteryPersistencePort.getById(catteryId)
                .orElseThrow(() -> new ApiException("Chatterie introuvable", HttpStatus.BAD_REQUEST));

        // Admin ou admin de la chatterie
        boolean isCreator = cattery.getCreatedByUserId().equals(operator.getId());
        if (!operator.isAdmin() && !isCreator) {
            throw new ApiException("Seul un administrateur ou l'administrateur de la chatterie peut la modifier", HttpStatus.FORBIDDEN);
        }

        // Récupère l'utilisateur par son id ou son email, sinon il est null
        Optional<User> targetUser =
            userId != null ? userAccessPort.getById(userId)
            : email != null ? userAccessPort.getByEmail(email)
            : Optional.empty();

        // L'utilisateur n'existe pas
        if (targetUser.isEmpty()) {
            throw new ApiException("Utilisateur à ajouter introuvable", HttpStatus.BAD_REQUEST);
        }

        Long targetUserId = targetUser.get().getId();
        // L'utilisateur fait déjà partie de la chatterie
        if (targetUserId.equals(cattery.getCreatedByUserId())) {
            throw new ApiException("L'utilisateur est déjà administrateur de cette chatterie", HttpStatus.CONFLICT);
        } else if (catteryUserPersistencePort.isUserMemberOfCattery(userId, catteryId)) {
            throw new ApiException("L'utilisateur est déjà membre de cette chatterie", HttpStatus.CONFLICT);
        }

        // Ajout de l'utilisateur
        catteryUserPersistencePort.addUserToCattery(catteryId, targetUserId);
    }

    // TODO : Splitter ce mastodonte
    @Override
    public void removeUserFromCattery(Long catteryId, Long userIdToRemove, OperatorUser operator) {
        // Vérifie que la chatterie existe
        Cattery cattery = catteryPersistencePort.getById(catteryId)
                .orElseThrow(() -> new ApiException("Chatterie introuvable", HttpStatus.BAD_REQUEST));

        // Vérifie que l'utilisateur à retirer existe
        userAccessPort.getById(userIdToRemove)
            .orElseThrow(() -> new ApiException("Utilisateur à retirer introuvable", HttpStatus.BAD_REQUEST));

        // Vérifie que ce n'est pas le créateur
        if (userIdToRemove.equals(cattery.getCreatedByUserId())) {
            throw new ApiException("Impossible de retirer l'administrateur de la chatterie", HttpStatus.FORBIDDEN);
        }

        // Vérifie les droits de l'opérateur
        boolean isCreator = cattery.getCreatedByUserId().equals(operator.getId());
        if (!operator.isAdmin() && !isCreator) {
            throw new ApiException("Seul un administrateur ou le créateur de la chatterie peut retirer un membre", HttpStatus.FORBIDDEN);
        }

        // Vérifie que le membre fait bien partie de la chatterie
        if (!catteryUserPersistencePort.isUserMemberOfCattery(catteryId, userIdToRemove)) {
            throw new ApiException("Cet utilisateur n'est pas membre de cette chatterie", HttpStatus.NOT_FOUND);
        }

        // Supprime l'entrée
        catteryUserPersistencePort.removeUserFromCattery(catteryId, userIdToRemove);
    }

    private CatteryDetails toDetails(Cattery cattery) {
        User createdBy = userAccessPort.getById(cattery.getCreatedByUserId())
                .orElse(new User(cattery.getCreatedByUserId())); // ou exception

        // Membres (filtre les absents si user supprimé par exemple)
        List<User> members = catteryUserPersistencePort.getByCatteryId(cattery.getId()).stream()
                .map(cu -> userAccessPort.getById(cu.getUserId()))
                .flatMap(Optional::stream)
                .toList();

        return new CatteryDetails(
                cattery.getId(),
                createdBy,
                null,
                members
        );
    }
    private CatteryDetails toDetails(Cattery cattery, User createdBy, Breeder breeder) {
        return new CatteryDetails(
                cattery.getId(),
                createdBy,
                breeder,
                null
        );
    }

    private List<CatteryDetails> getAllAccessibleFromUser(Long userId) {
        List<Cattery> created = catteryPersistencePort.getByCreatedByUserId(userId);
        List<CatteryUser> memberships = catteryUserPersistencePort.getByUserId(userId);

        List<Cattery> memberOf = memberships.stream()
                .map(cu -> catteryPersistencePort.getById(cu.getCatteryId()))
                .flatMap(Optional::stream)
                .toList();

        List<Cattery> merged = Stream.concat(created.stream(), memberOf.stream())
            .collect(Collectors.collectingAndThen(
                Collectors.toMap(Cattery::getId, Function.identity(), (a, b) -> a),
                map -> new ArrayList<>(map.values())
            ));

        return merged.stream()
                .map(this::toDetails)
                .toList();
    }
}
