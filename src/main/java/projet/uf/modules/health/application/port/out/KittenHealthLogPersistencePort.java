package projet.uf.modules.health.application.port.out;

import projet.uf.modules.health.domain.model.KittenHealthLog;

import java.util.Optional;

public interface KittenHealthLogPersistencePort {
    Optional<KittenHealthLog> getById(Long id);
    Optional<KittenHealthLog> getByHealthLogId(Long healthLogId);
    KittenHealthLog save(KittenHealthLog kittenHealthLog);
    void deleteById(Long id);
}