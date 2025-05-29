package projet.uf.modules.breeder.adapter.out.persistence.catteryuser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCatteryUserRepository extends JpaRepository<CatteryUserEntity, CatteryUserId> {
    boolean existsByCatteryIdAndUserId(Long catteryId, Long userId);
    List<CatteryUserEntity> findByCatteryId(Long catteryId);
    List<CatteryUserEntity> findByUserId(Long userId);
}
