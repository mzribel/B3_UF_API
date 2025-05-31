package projet.uf.modules.health.adapter.out.persistence.gestationhealthlog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaGestationHealthLogRepository extends JpaRepository<GestationHealthLogEntity, Long> {
    List<GestationHealthLogEntity> findByGestationId(Long gestationId);
    Optional<GestationHealthLogEntity> findByHealthLogId(Long healthLogId);
}