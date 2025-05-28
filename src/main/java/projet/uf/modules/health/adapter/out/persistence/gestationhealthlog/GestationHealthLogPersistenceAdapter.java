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
        return jpaGestationHealthLogRepository.findById(id).map(GestationHealthLogEntityMapper::toModel);
    }

    @Override
    public List<GestationHealthLog> getByGestationId(Long gestationId) {
        return jpaGestationHealthLogRepository.findByGestationId(gestationId)
                .stream()
                .map(GestationHealthLogEntityMapper::toModel)
                .toList();
    }

    @Override
    public Optional<GestationHealthLog> getByHealthLogId(Long healthLogId) {
        return jpaGestationHealthLogRepository.findByHealthLogId(healthLogId).map(GestationHealthLogEntityMapper::toModel);
    }

    @Override
    public GestationHealthLog save(GestationHealthLog gestationHealthLog) {
        GestationHealthLogEntity entity = GestationHealthLogEntityMapper.toEntity(gestationHealthLog);
        GestationHealthLogEntity saved = jpaGestationHealthLogRepository.save(entity);
        return GestationHealthLogEntityMapper.toModel(saved);
    }

    @Override
    public void deleteById(Long id) {
        jpaGestationHealthLogRepository.deleteById(id);
    }
}