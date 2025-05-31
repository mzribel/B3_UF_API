package projet.uf.modules.health.application;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.cat.application.ports.in.CatAuthorizationUseCase;
import projet.uf.modules.health.application.port.in.HealthLogAuthorizationUseCase;
import projet.uf.modules.health.application.port.out.HealthLogPersistencePort;
import projet.uf.modules.health.domain.model.HealthLog;

@AllArgsConstructor
public class HealthLogAuthorizationService implements HealthLogAuthorizationUseCase {
    private final HealthLogPersistencePort healthLogPersistencePort;
    private final CatAuthorizationUseCase catAccessUseCase;

    @Override
    public HealthLog getHealthLogOrThrow(@NotNull Long healthLogId, @NotNull OperatorUser operator) {
        HealthLog healthLog = healthLogPersistencePort.getById(healthLogId)
                .orElseThrow(() -> new ApiException("Entrée de santé introuvable", HttpStatus.BAD_REQUEST));

        if (!operator.isAdmin() && catAccessUseCase.hasUserAccessToCat(healthLog.getCatId(), operator)) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }
        return healthLog;
    }



    @Override
    public boolean hasUserAccessToHealthLog(@NotNull Long healthLogId, @NotNull OperatorUser operator) {
        HealthLog healthLog = healthLogPersistencePort.getById(healthLogId)
                .orElseThrow(() -> new ApiException("Entrée de santé introuvable", HttpStatus.BAD_REQUEST));

        return (operator.isAdmin() || catAccessUseCase.hasUserAccessToCat(healthLog.getCatId(), operator));
    }

}
