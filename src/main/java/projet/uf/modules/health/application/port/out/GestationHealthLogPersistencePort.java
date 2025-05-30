package projet.uf.modules.health.application.port.out;

import projet.uf.modules.health.domain.model.GestationHealthLog;

import java.util.List;
import java.util.Optional;

public interface GestationHealthLogPersistencePort {
    Optional<GestationHealthLog> getById(Long id);
    List<GestationHealthLog> getByGestationId(Long gestationId);
    Optional<GestationHealthLog> getByHealthLogId(Long healthLogId);
    GestationHealthLog save(GestationHealthLog gestationHealthLog);
    void deleteById(Long id);
}