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
                && catteryUserPersistencePort.isUserMemberOfCattery(catteryId, operator.getId())
        ) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }
        return cattery;
    }

    @Override
    public boolean hasUserAccessToCattery(Long catteryId, OperatorUser operator) {
        if (operator.isAdmin()) {
            return true;
        }

        if (!catteryUserPersistencePort.getByCatteryId(catteryId).isEmpty()) {
            throw  new ApiException("Chatterie introuvable", HttpStatus.BAD_REQUEST);
        }

        return (operator.isAdmin()
                || catteryPersistencePort.isUserAdminOfCattery(operator.getId(), catteryId)
                || catteryUserPersistencePort.isUserMemberOfCattery(operator.getId(), catteryId)
        );
    }
}
