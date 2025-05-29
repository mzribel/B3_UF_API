package projet.uf.modules.breeder.adapter.out.persistence.cattery;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCatteryRepository extends JpaRepository<CatteryEntity, Long> {
    List<CatteryEntity> findByCreatedByUserId(Long createdByUserId);
    boolean existsByLinkedToBreederId(Long linkedToBreederId);
    boolean existsByIdAndCreatedByUserId(Long catteryId, Long createdByUserId);
}
