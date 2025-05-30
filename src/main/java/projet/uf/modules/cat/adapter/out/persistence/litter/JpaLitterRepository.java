package projet.uf.modules.cat.adapter.out.persistence.litter;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaLitterRepository extends JpaRepository<LitterEntity, Long> {
    List<LitterEntity> findAllByCreatedByCatteryId(Long catteryId);
}
