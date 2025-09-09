package projet.uf.modules.breeder.application;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.port.in.CatteryAuthorizationUseCase;
import projet.uf.modules.breeder.application.port.out.CatteryPersistencePort;
import projet.uf.modules.breeder.application.port.out.CatteryUserPersistencePort;
import projet.uf.modules.breeder.domain.model.Cattery;

import java.util.Objects;

@AllArgsConstructor
public class CatteryAuthorizationService implements CatteryAuthorizationUseCase {
    private final CatteryPersistencePort catteryPersistencePort;
    private final CatteryUserPersistencePort catteryUserPersistencePort;

    @Override
    public Cattery getCatteryOrThrow(@NotNull Long catteryId, OperatorUser operator) {
        Cattery cattery = catteryPersistencePort.getById(catteryId)
                .orElseThrow(() -> new ApiException("Chatterie introuvable", HttpStatus.BAD_REQUEST));

        if (!operator.isAdmin()
                && !Objects.equals(operator.getId(), cattery.getCreatedByUserId())
                && !catteryUserPersistencePort.isUserMemberOfCattery(catteryId, operator.getId())) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }
        return cattery;
    }

    @Override
    public boolean hasUserAccessToCattery(Long catteryId, OperatorUser operator) {
        // 1) Admin : accès immédiat
        if (operator.isAdmin()) {
            return true;
        }

        // 2) Existence via le port "cattery"
        var catteryOpt = catteryPersistencePort.getById(catteryId);
        if (catteryOpt.isEmpty()) {
            throw new ApiException("Chatterie introuvable", HttpStatus.BAD_REQUEST);
        }

        // 3) Règles d'accès
        return catteryPersistencePort.isUserAdminOfCattery(operator.getId(), catteryId)
                || catteryUserPersistencePort.isUserMemberOfCattery(operator.getId(), catteryId);
    }
}
