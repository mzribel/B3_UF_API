package projet.uf.modules.breeder.application;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.command.AddUserToCatteryCommand;
import projet.uf.modules.breeder.application.dto.CatteryDetailsDto;
import projet.uf.modules.breeder.application.command.CreateCatteryCommand;
import projet.uf.modules.breeder.application.dto.UserCatteriesDto;
import projet.uf.modules.breeder.application.port.in.BreederUseCase;
import projet.uf.modules.breeder.application.port.in.CatteryAuthorizationUseCase;
import projet.uf.modules.breeder.application.port.in.CatteryUseCase;
import projet.uf.modules.breeder.application.port.out.CatteryPersistencePort;
import projet.uf.modules.breeder.application.port.out.CatteryUserPersistencePort;
import projet.uf.modules.breeder.domain.model.Breeder;
import projet.uf.modules.breeder.domain.model.Cattery;
import projet.uf.modules.breeder.domain.model.CatteryUser;
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
    // TODO : ranger les dépendances
    private final CatteryPersistencePort catteryPersistencePort;
    private final CatteryUserPersistencePort catteryUserPersistencePort;
    private final BreederUseCase breederUseCase;
    private final UserPersistencePort userPersistencePort;
    private final CatteryDtoAssembler dtoAssembler;
    private final CatteryAuthorizationUseCase catteryAccessUseCase;

    @Override
    @Transactional
    public CatteryDetailsDto create(CreateCatteryCommand command, OperatorUser operatorUser) {
        if (!operatorUser.isAdmin() && command.userId() != null && !command.userId().equals(operatorUser.getId())) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }

        Long userId = operatorUser.isAdmin() && command.userId() != null ? command.userId() : operatorUser.getId();
        userPersistencePort.getById(userId)
                .orElseThrow(() -> new ApiException("Utilisateur introuvable", HttpStatus.BAD_REQUEST));

        // Créé la chatterie
        Cattery newCattery = catteryPersistencePort.insert(new Cattery(userId));
        // Crée l'éleveur associé
        Breeder breeder = breederUseCase.createEmptyCatteryBreeder(command.name(), newCattery.getId());
        // Ajoute l'éleveur en tant qu'éleveur de la chatterie
        newCattery.setLinkedToBreederId(breeder.getId());
        catteryPersistencePort.update(newCattery);

        return dtoAssembler.toDetailsDto(newCattery);
    }

    @Override
    public CatteryDetailsDto getById(Long catteryId, OperatorUser operator) {
        Cattery cattery = catteryAccessUseCase.getCatteryOrThrow(catteryId, operator);
        return dtoAssembler.toDetailsDto(cattery);
    }

    @Override
    public List<CatteryDetailsDto> getAll(OperatorUser operator) {
        if (operator.isAdmin()) {
            return catteryPersistencePort.getAll().stream().map(dtoAssembler::toDetailsDto).toList();
        }
        return getAllAccessibleFromUser(operator.getId());
    }

    @Override
    public void deleteById(Long id, OperatorUser operator) {
        catteryAccessUseCase.getCatteryOrThrow(id, operator);
        catteryPersistencePort.deleteById(id);
    }

    // TODO : Splitter ce mastodonte
    @Override
    public void addUserToCattery(Long catteryId, AddUserToCatteryCommand command, OperatorUser operator) {
        Cattery cattery = catteryAccessUseCase.getCatteryOrThrow(catteryId, operator);

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
        Cattery cattery = catteryAccessUseCase.getCatteryOrThrow(catteryId, operator);

        // Vérifie que l'utilisateur à retirer existe
        userPersistencePort.getById(userIdToRemove)
            .orElseThrow(() -> new ApiException("Utilisateur introuvable", HttpStatus.BAD_REQUEST));

        // Vérifie que ce n'est pas le créateur
        if (userIdToRemove.equals(cattery.getCreatedByUserId())) {
            throw new ApiException("Impossible de retirer l'administrateur de la chatterie", HttpStatus.FORBIDDEN);
        }

        // Vérifie que le membre fait bien partie de la chatterie
        if (!catteryUserPersistencePort.isUserMemberOfCattery(catteryId, userIdToRemove)) {
            throw new ApiException("Cet utilisateur n'est pas membre de cette chatterie", HttpStatus.NOT_FOUND);
        }

        // Supprime l'entrée
        catteryUserPersistencePort.removeUserFromCattery(catteryId, userIdToRemove);
    }

    @Override
    public void promoteUserAdminOfCattery(Long userId, OperatorUser operator, Long catteryId) {
        Cattery cattery = catteryAccessUseCase.getCatteryOrThrow(catteryId, operator);
        if (!operator.isAdmin() && !Objects.equals(operator.getId(), cattery.getCreatedByUserId())) {
            throw new ApiException("Seul l'administrateur de la chatterie peut modifier l'administrateur de cette chatterie", HttpStatus.FORBIDDEN);
        }

        // Récupère l'utilisateur
        User newAdmin = userPersistencePort.getById(userId).orElseThrow(()-> new ApiException("Utilisateur introuvable", HttpStatus.BAD_REQUEST));

        // ERR: Utilisateur est déjà admin
        if (newAdmin.getId().equals(cattery.getCreatedByUserId())) {
            throw new ApiException("L'utilisateur est déjà admin de cette chatterie", HttpStatus.FORBIDDEN);
        }
        // ERR: L'utilisateur n'est pas membre de la chatterie
        if (!catteryUserPersistencePort.isUserMemberOfCattery(catteryId, userId)) {
            throw new ApiException("Un utilisateur doit d'abord être membre de la chatterie pour pouvoir en devenir administrateur", HttpStatus.BAD_REQUEST);
        }

        // Enregistre si l'administrateur actuel passe son rôle à un utilisateur
        boolean isDemotingSelf = Objects.equals(operator.getId(), cattery.getCreatedByUserId()) &&
                !Objects.equals(operator.getId(), userId) &&
                !catteryUserPersistencePort.isUserMemberOfCattery(catteryId, operator.getId());

        // Met à jour la chatterie avec le nouvel admin
        cattery.setCreatedByUserId(newAdmin.getId());
        catteryPersistencePort.update(cattery);

        // Retire le nouveau membre des utilisateurs
        catteryUserPersistencePort.removeUserFromCattery(catteryId, userId);
        if (isDemotingSelf) {
            catteryUserPersistencePort.addUserToCattery(catteryId, operator.getId());
        }
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
                .stream().map(dtoAssembler::toDetailsDto).toList();
    }

    public List<CatteryDetailsDto> getCatteriesWhereUserIsMember(Long userId) {
        return catteryUserPersistencePort.getAllCatteriesByUserId(userId)
                .stream().map(dtoAssembler::toDetailsDto).toList();
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
                .map(dtoAssembler::toDetailsDto)
                .toList();
    }
}
