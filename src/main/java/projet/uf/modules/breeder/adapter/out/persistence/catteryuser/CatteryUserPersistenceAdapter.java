package projet.uf.modules.breeder.adapter.out.persistence.catteryuser;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.breeder.adapter.out.persistence.cattery.CatteryEntity;
import projet.uf.modules.breeder.adapter.out.persistence.cattery.JpaCatteryRepository;
import projet.uf.modules.breeder.application.port.out.CatteryUserPersistencePort;
import projet.uf.modules.breeder.domain.model.CatteryUser;
import projet.uf.modules.user.adapter.out.persistence.JpaUserRepository;
import projet.uf.modules.user.adapter.out.persistence.UserEntity;

import java.util.List;

@AllArgsConstructor
public class CatteryUserPersistenceAdapter implements CatteryUserPersistencePort {
    private final JpaCatteryUserRepository jpaCatteryUserRepository;
    private final JpaUserRepository jpaUserRepository;
    private final JpaCatteryRepository jpaCatteryRepository;

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
    public boolean isUserMemberOfCattery(Long catteryId, Long userId) {
        return jpaCatteryUserRepository.existsByCatteryIdAndUserId(catteryId, userId);
    }

    @Override
    public void addUserToCattery(Long catteryId, Long userId) {
        CatteryEntity cattery = jpaCatteryRepository.findById(catteryId)
                .orElseThrow(() -> new ApiException("Chatterie introuvable", HttpStatus.BAD_REQUEST));

        UserEntity user = jpaUserRepository.findById(userId)
                .orElseThrow(() -> new ApiException("Utilisateur introuvable", HttpStatus.BAD_REQUEST));

        CatteryUserEntity entity = new CatteryUserEntity(cattery, user);
        jpaCatteryUserRepository.save(entity);
    }

    @Override
    public void removeUserFromCattery(Long catteryId, Long userId) {
        CatteryUserId id = new CatteryUserId(catteryId, userId);

        if (!jpaCatteryUserRepository.existsByCatteryIdAndUserId(catteryId, userId)) {
            throw new ApiException("L'utilisateur n'est pas membre de cette chatterie", HttpStatus.BAD_REQUEST);
        }

        jpaCatteryUserRepository.deleteById(id);
    }
}
