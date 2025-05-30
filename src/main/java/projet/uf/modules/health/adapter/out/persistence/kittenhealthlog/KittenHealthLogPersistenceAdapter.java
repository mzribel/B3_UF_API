package projet.uf.modules.health.adapter.out.persistence.kittenhealthlog;

import lombok.AllArgsConstructor;
import projet.uf.modules.health.application.port.out.KittenHealthLogPersistencePort;
import projet.uf.modules.health.domain.model.KittenHealthLog;

import java.util.Optional;

@AllArgsConstructor
public class KittenHealthLogPersistenceAdapter implements KittenHealthLogPersistencePort {

    private final JpaKittenHealthLogRepository jpaKittenHealthLogRepository;

    @Override
    public Optional<KittenHealthLog> getById(Long id) {
        return jpaKittenHealthLogRepository.findById(id).map(KittenHealthLogEntity::toModel);
    }

    @Override
    public Optional<KittenHealthLog> getByHealthLogId(Long healthLogId) {
        return jpaKittenHealthLogRepository.findByHealthLogId(healthLogId).map(KittenHealthLogEntity::toModel);
    }

    @Override
    public KittenHealthLog save(KittenHealthLog kittenHealthLog) {
        KittenHealthLogEntity entity = KittenHealthLogEntity.toEntity(kittenHealthLog);
        KittenHealthLogEntity saved = jpaKittenHealthLogRepository.save(entity);
        return KittenHealthLogEntity.toModel(saved);
    }

    @Override
    public void deleteById(Long id) {
        jpaKittenHealthLogRepository.deleteById(id);
    }
}