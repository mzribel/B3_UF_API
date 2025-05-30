package projet.uf.modules.health.adapter.out.persistence.kittenhealthlog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaKittenHealthLogRepository extends JpaRepository<KittenHealthLogEntity, Long> {
    Optional<KittenHealthLogEntity> findByHealthLogId(Long healthLogId);
}