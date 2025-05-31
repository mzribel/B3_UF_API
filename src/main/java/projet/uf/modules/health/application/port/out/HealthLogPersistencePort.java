package projet.uf.modules.health.application.port.out;

import projet.uf.modules.health.domain.model.HealthLog;

import java.util.List;
import java.util.Optional;

public interface HealthLogPersistencePort {
    Optional<HealthLog> getById(Long id);
    List<HealthLog> getAll();
    List<HealthLog> getByCatId(Long catId);
    HealthLog save(HealthLog healthLog);
    void deleteById(Long id);
}