package projet.uf.modules.breeder.adapter.out.persistence.catteryuser;

import lombok.AllArgsConstructor;
import projet.uf.modules.breeder.application.port.out.CatteryUserPersistencePort;
import projet.uf.modules.breeder.domain.model.CatteryUser;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class CatteryUserPersistenceAdapter implements CatteryUserPersistencePort {
    private final JpaCatteryUserRepository jpaCatteryUserRepository;

    @Override
    public Optional<CatteryUser> getByCatteryAndUserId(Long catteryId, Long userId) {
        return jpaCatteryUserRepository.findByCatteryIdAndUserId(catteryId, userId)
            .map(CatteryUserEntityMapper::toModel);
    }

    @Override
    public List<CatteryUser> getByUserId(Long id) {
        return jpaCatteryUserRepository.findByUserId(id)
            .stream()
            .map(CatteryUserEntityMapper::toModel)
            .toList();
    }

    @Override
    public List<CatteryUser> getByCatteryId(Long id) {
        return jpaCatteryUserRepository.findByCatteryId(id)
                .stream()
                .map(CatteryUserEntityMapper::toModel)
                .toList();
    }

    @Override
    public boolean isCatteryUser(Long catteryId, Long userId) {
        return jpaCatteryUserRepository.existsByCatteryIdAndUserId(catteryId, userId);
    }
}
