package projet.uf.modules.health.adapter.out.persistence.healthlog;

import lombok.AllArgsConstructor;
import projet.uf.modules.health.application.port.out.HealthLogPersistencePort;
import projet.uf.modules.health.domain.model.HealthLog;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class HealthLogPersistenceAdapter implements HealthLogPersistencePort {

    private final JpaHealthLogRepository jpaHealthLogRepository;

    @Override
    public Optional<HealthLog> getById(Long id) {
        return jpaHealthLogRepository.findById(id).map(HealthLogEntity::toModel);
    }

    @Override
    public List<HealthLog> getAll() {
        return jpaHealthLogRepository.findAll()
                .stream()
                .map(HealthLogEntity::toModel)
                .toList();
    }

    @Override
    public List<HealthLog> getByCatId(Long catId) {
        return jpaHealthLogRepository.findByCatId(catId)
                .stream()
                .map(HealthLogEntity::toModel)
                .toList();
    }

    @Override
    public HealthLog save(HealthLog healthLog) {
        HealthLogEntity entity = HealthLogEntity.toEntity(healthLog);
        HealthLogEntity saved = jpaHealthLogRepository.save(entity);
        return HealthLogEntity.toModel(saved);
    }

    @Override
    public void deleteById(Long id) {
        jpaHealthLogRepository.deleteById(id);
    }
}