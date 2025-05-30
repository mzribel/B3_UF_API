package projet.uf.modules.health.adapter.out.persistence.healthlog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaHealthLogRepository extends JpaRepository<HealthLogEntity, Long> {
    List<HealthLogEntity> findByCatId(Long catId);
}