package projet.uf.modules.breeder.application;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.command.AddUserToCatteryCommand;
import projet.uf.modules.breeder.application.dto.CatteryDetailsDto;
import projet.uf.modules.breeder.application.command.CreateCatteryCommand;
import projet.uf.modules.breeder.application.dto.UserCatteriesDto;
import projet.uf.modules.breeder.application.port.in.BreederUseCase;
import projet.uf.modules.breeder.application.port.in.CatteryUseCase;
import projet.uf.modules.breeder.application.port.out.BreederPersistencePort;
import projet.uf.modules.breeder.application.port.out.CatteryPersistencePort;
import projet.uf.modules.breeder.application.port.out.CatteryUserPersistencePort;
import projet.uf.modules.breeder.domain.model.Breeder;
import projet.uf.modules.breeder.domain.model.Cattery;
import projet.uf.modules.breeder.domain.model.CatteryUser;
import projet.uf.modules.user.application.dto.UserDto;
import projet.uf.modules.user.application.port.out.UserPersistencePort;
import projet.uf.modules.user.domain.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private final BreederPersistencePort breederPersistencePort;
    private final BreederUseCase breederUseCase;
    private final UserPersistencePort userPersistencePort;

    @Override
    public CatteryDetailsDto create(CreateCatteryCommand command, OperatorUser operatorUser) {
        if (!operatorUser.isAdmin() && command.userId() != null && !command.userId().equals(operatorUser.getId())) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }

        Long userId = operatorUser.isAdmin() && command.userId() != null ? command.userId() : operatorUser.getId();

        User user = userPersistencePort.getById(userId)
                .orElseThrow(() -> new ApiException("Utilisateur introuvable", HttpStatus.BAD_REQUEST));

        Cattery newCattery = catteryPersistencePort.insert(new Cattery(userId));
        Breeder breeder = breederUseCase.createEmptyCatteryBreeder(command.name(), newCattery.getId());
        newCattery.setLinkedToBreederId(breeder.getId());
        catteryPersistencePort.update(newCattery);

        return this.toDetails(newCattery, user, breeder);
    }

    @Override
    public CatteryDetailsDto getById(Long catteryId, OperatorUser operator) {
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
    public List<CatteryDetailsDto> getAll(OperatorUser operator) {
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
    public void addUserToCattery(Long catteryId, AddUserToCatteryCommand command, OperatorUser operator) {
        // Récupère la chatterie
        Cattery cattery = catteryPersistencePort.getById(catteryId)
                .orElseThrow(() -> new ApiException("Chatterie introuvable", HttpStatus.BAD_REQUEST));

        // Vérifie les droits : admin global ou admin de la chatterie
        boolean isCreator = cattery.getCreatedByUserId().equals(operator.getId());
        if (!operator.isAdmin() && !isCreator) {
            throw new ApiException("Seul un administrateur ou l'administrateur de la chatterie peut la modifier", HttpStatus.FORBIDDEN);
        }

        // Récupère les champs depuis le command
        Long userId = command.userId();
        String email = command.userEmail();

        // Récupère l'utilisateur cible par id ou email
        Optional<User> targetUser = (userId != null)
                ? userPersistencePort.getById(userId)
                : (email != null ? userPersistencePort.getByEmail(email) : Optional.empty());

        if (targetUser.isEmpty()) {
            throw new ApiException("Utilisateur introuvable", HttpStatus.BAD_REQUEST);
        }

        Long targetUserId = targetUser.get().getId();

        // Vérifie s’il est déjà administrateur ou membre
        if (targetUserId.equals(cattery.getCreatedByUserId())) {
            throw new ApiException("L'utilisateur est déjà administrateur de cette chatterie", HttpStatus.CONFLICT);
        }

        if (catteryUserPersistencePort.isUserMemberOfCattery(targetUserId, catteryId)) {
            throw new ApiException("L'utilisateur est déjà membre de cette chatterie", HttpStatus.CONFLICT);
        }

        // Ajoute l'utilisateur à la chatterie
        catteryUserPersistencePort.addUserToCattery(catteryId, targetUserId);
    }


    // TODO : Splitter ce mastodonte
    @Override
    public void removeUserFromCattery(Long catteryId, Long userIdToRemove, OperatorUser operator) {
        // Vérifie que la chatterie existe
        Cattery cattery = catteryPersistencePort.getById(catteryId)
                .orElseThrow(() -> new ApiException("Chatterie introuvable", HttpStatus.BAD_REQUEST));

        // Vérifie que l'utilisateur à retirer existe
        userPersistencePort.getById(userIdToRemove)
            .orElseThrow(() -> new ApiException("Utilisateur introuvable", HttpStatus.BAD_REQUEST));

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

    @Override
    public UserCatteriesDto getUserCatteries(Long id, OperatorUser operator) {
        if (!operator.isAdmin() && !Objects.equals(id, operator.getId())) {
            throw new ApiException("Accès non autorisé", HttpStatus.FORBIDDEN);
        }

        Long userId = operator.isAdmin() && id != null ? id : operator.getId();
        if (!userPersistencePort.existsById(userId)) {
            throw new ApiException("Utilisateur introuvable", HttpStatus.BAD_REQUEST);
        }

        return new UserCatteriesDto(
                getCatteriesWhereUserIsAdmin(userId),
                getCatteriesWhereUserIsMember(userId)
        );
    }

    public List<CatteryDetailsDto> getCatteriesWhereUserIsAdmin(Long userId) {
        return catteryPersistencePort.getByCreatedByUserId(userId)
                .stream().map(this::toDetails).toList();
    }

    public List<CatteryDetailsDto> getCatteriesWhereUserIsMember(Long userId) {
        return catteryUserPersistencePort.getAllCatteriesByUserId(userId)
                .stream().map(this::toDetails).toList();
    }

    private CatteryDetailsDto toDetails(Cattery cattery) {
        User createdBy = userPersistencePort.getById(cattery.getCreatedByUserId())
            .orElse(new User(cattery.getCreatedByUserId())); // ou exception

        // Membres (filtre les absents si user supprimé par exemple)
        List<User> members = catteryUserPersistencePort.getByCatteryId(cattery.getId()).stream()
            .map(cu -> userPersistencePort.getById(cu.getUserId()))
            .flatMap(Optional::stream)
            .toList();

        Breeder breeder =
            cattery.getLinkedToBreederId() != null
            ? breederPersistencePort.getById(cattery.getLinkedToBreederId()).orElse(null)
            : null;

        return new CatteryDetailsDto(
            cattery.getId(),
            UserDto.toDto(createdBy),
            breeder,
            members
        );
    }
    private CatteryDetailsDto toDetails(Cattery cattery, User createdBy, Breeder breeder) {
        return new CatteryDetailsDto(
            cattery.getId(),
            UserDto.toDto(createdBy),
            breeder,
            null
        );
    }

    private List<CatteryDetailsDto> getAllAccessibleFromUser(Long userId) {
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
