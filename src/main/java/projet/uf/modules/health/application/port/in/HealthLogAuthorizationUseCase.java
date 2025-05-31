package projet.uf.modules.health.application.port.in;

import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.health.domain.model.HealthLog;

public interface HealthLogAuthorizationUseCase {
    HealthLog getHealthLogOrThrow(Long healthLogId, OperatorUser operator);
    boolean hasUserAccessToHealthLog(Long healthLogId, OperatorUser operator);
}
