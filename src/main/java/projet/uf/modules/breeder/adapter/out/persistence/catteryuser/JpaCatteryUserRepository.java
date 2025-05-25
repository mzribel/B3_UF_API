package projet.uf.modules.breeder.adapter.out.persistence.catteryuser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaCatteryUserRepository extends JpaRepository<CatteryUserEntity, Long> {
    boolean existsByCatteryIdAndUserId(Long catteryId, Long userId);
    Optional<CatteryUserEntity> findByCatteryId(Long catteryId);
    Optional<CatteryUserEntity> findByUserId(Long userId);
    Optional<CatteryUserEntity> findByCatteryIdAndUserId(Long catteryId, Long userId);
}
