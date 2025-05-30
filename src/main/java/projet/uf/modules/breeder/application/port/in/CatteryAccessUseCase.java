package projet.uf.modules.breeder.application.port.in;

import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.domain.model.Cattery;

public interface CatteryAccessUseCase {
    Cattery getCatteryOrThrow(Long catteryId, OperatorUser operator);
    boolean hasUserAccessToCattery(Long catteryId, OperatorUser operator);
}
