package projet.uf.modules.breeder.application.port.out;

import projet.uf.modules.breeder.domain.model.Cattery;
import projet.uf.modules.breeder.domain.model.CatteryUser;

import java.util.List;

public interface CatteryUserPersistencePort {
    List<CatteryUser> getByUserId(Long id);
    List<CatteryUser> getByCatteryId(Long id);
    boolean isUserMemberOfCattery(Long catteryId, Long userId);
    void addUserToCattery(Long catteryId, Long userId);
    void removeUserFromCattery(Long catteryId, Long userId);
    List<Cattery> getAllCatteriesByUserId(Long userId);
}
