package projet.uf.modules.breeder.application.port.out;

import projet.uf.modules.breeder.domain.model.CatteryUser;

import java.util.List;
import java.util.Optional;

public interface CatteryUserPersistencePort {
    Optional<CatteryUser> getByCatteryAndUserId(Long catteryId, Long userId);
    List<CatteryUser> getByUserId(Long id);
    List<CatteryUser> getByCatteryId(Long id);
    boolean isCatteryUser(Long catteryId, Long userId);
}
