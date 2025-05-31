package projet.uf.modules.health.adapter.out.persistence.gestationhealthlog;

import lombok.AllArgsConstructor;
import projet.uf.modules.health.application.port.out.GestationHealthLogPersistencePort;
import projet.uf.modules.health.domain.model.GestationHealthLog;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class GestationHealthLogPersistenceAdapter implements GestationHealthLogPersistencePort {

    private final JpaGestationHealthLogRepository jpaGestationHealthLogRepository;

    @Override
    public Optional<GestationHealthLog> getById(Long id) {
        return jpaGestationHealthLogRepository.findById(id).map(GestationHealthLogEntity::toModel);
    }

    @Override
    public List<GestationHealthLog> getByGestationId(Long gestationId) {
        return jpaGestationHealthLogRepository.findByGestationId(gestationId)
                .stream()
                .map(GestationHealthLogEntity::toModel)
                .toList();
    }

    @Override
    public Optional<GestationHealthLog> getByHealthLogId(Long healthLogId) {
        return jpaGestationHealthLogRepository.findByHealthLogId(healthLogId).map(GestationHealthLogEntity::toModel);
    }

    @Override
    public GestationHealthLog save(GestationHealthLog gestationHealthLog) {
        GestationHealthLogEntity entity = GestationHealthLogEntity.toEntity(gestationHealthLog);
        GestationHealthLogEntity saved = jpaGestationHealthLogRepository.save(entity);
        return GestationHealthLogEntity.toModel(saved);
    }

    @Override
    public void deleteById(Long id) {
        jpaGestationHealthLogRepository.deleteById(id);
    }
}