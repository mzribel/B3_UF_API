package projet.uf.modules.breeder.application.port;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.breeder.application.model.CatteryDetails;
import projet.uf.modules.breeder.application.port.in.CatteryUseCase;
import projet.uf.modules.breeder.application.port.out.CatteryPersistencePort;
import projet.uf.modules.breeder.application.port.out.CatteryUserPersistencePort;
import projet.uf.modules.breeder.application.port.out.UserAccessPort;
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
    private final UserAccessPort userAccessPort;

    @Override
    public Cattery create(Long createdByUserId) {
        if (!userAccessPort.existsById(createdByUserId)) {
            throw new ApiException("Utilisateur introuvable", HttpStatus.BAD_REQUEST);
        }

        Cattery newCattery = new Cattery(createdByUserId);
        return catteryPersistencePort.insert(newCattery);
    }

    @Override
    public Cattery getById(Long id) {
        return catteryPersistencePort.getById(id)
                .orElseThrow(() -> new ApiException("Chatterie introuvable", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Cattery> getAll() {
        return catteryPersistencePort.getAll();
    }

    @Override
    public CatteryDetails getCatteryDetailsById(Long catteryId) {
        return catteryPersistencePort.getById(catteryId)
                .map(this::toDetails)
                .orElseThrow(() -> new ApiException("Chatterie introuvable", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<CatteryDetails> getAllCatteryDetails() {
        return catteryPersistencePort.getAll().stream().map(this::toDetails).toList();
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

    public List<CatteryDetails> getAllAccessibleByUser(Long userId) {
        List<Cattery> created = catteryPersistencePort.getByCreatedByUserId(userId);
        List<CatteryUser> memberships = catteryUserPersistencePort.getByUserId(userId);

        List<Cattery> memberOf = memberships.stream()
                .map(cu -> catteryPersistencePort.getById(cu.getCatteryId()))
                .flatMap(Optional::stream)
                .toList();

        // Fusion sans doublons
        List<Cattery> merged = Stream.concat(created.stream(), memberOf.stream())
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(Cattery::getId, Function.identity(), (a, b) -> a),
                        map -> new ArrayList<>(map.values())
                ));

        return merged.stream()
                .map(this::toDetails)
                .toList();
    }

    @Override
    public CatteryDetails getByIdIfAuthorized(Long catteryId, Long userId) {
        Cattery cattery = catteryPersistencePort.getById(catteryId)
                .orElseThrow(() -> new ApiException("Chatterie introuvable", HttpStatus.NOT_FOUND));

        boolean isCreator = cattery.getCreatedByUserId().equals(userId);

        boolean isMember = catteryUserPersistencePort.getByCatteryId(catteryId).stream()
                .anyMatch(cu -> cu.getUserId().equals(userId));

        if (!isCreator && !isMember) {
            throw new ApiException("Accès interdit à cette chatterie", HttpStatus.FORBIDDEN);
        }

        return this.toDetails(cattery);
    }
}
