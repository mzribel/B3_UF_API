package projet.uf.modules.health.application.port;

import lombok.AllArgsConstructor;
import projet.uf.modules.health.application.port.in.HealthLogUseCase;
import projet.uf.modules.health.application.port.out.GestationHealthLogPersistencePort;
import projet.uf.modules.health.application.port.out.HealthLogPersistencePort;
import projet.uf.modules.health.application.port.out.KittenHealthLogPersistencePort;
import projet.uf.modules.health.domain.model.GestationHealthLog;
import projet.uf.modules.health.domain.model.HealthLog;
import projet.uf.modules.health.domain.model.KittenHealthLog;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class HealthLogService implements HealthLogUseCase {
    private final HealthLogPersistencePort healthLogPersistencePort;
    private final KittenHealthLogPersistencePort kittenHealthLogPersistencePort;
    private final GestationHealthLogPersistencePort gestationHealthLogPersistencePort;

    @Override
    public HealthLog createHealthLog(HealthLog healthLog) {
        return healthLogPersistencePort.save(healthLog);
    }

    @Override
    public KittenHealthLog createKittenHealthLog(KittenHealthLog kittenHealthLog) {
        return kittenHealthLogPersistencePort.save(kittenHealthLog);
    }

    @Override
    public GestationHealthLog createGestationHealthLog(GestationHealthLog gestationHealthLog) {
        return gestationHealthLogPersistencePort.save(gestationHealthLog);
    }

    @Override
    public Optional<HealthLog> getHealthLogById(Long id) {
        return healthLogPersistencePort.getById(id);
    }

    @Override
    public List<HealthLog> getAllHealthLogs() {
        return healthLogPersistencePort.getAll();
    }

    @Override
    public List<HealthLog> getHealthLogsByCatId(Long catId) {
        return healthLogPersistencePort.getByCatId(catId);
    }

    @Override
    public Optional<KittenHealthLog> getKittenHealthLogById(Long id) {
        return kittenHealthLogPersistencePort.getById(id);
    }

    @Override
    public Optional<KittenHealthLog> getKittenHealthLogByHealthLogId(Long healthLogId) {
        return kittenHealthLogPersistencePort.getByHealthLogId(healthLogId);
    }

    @Override
    public Optional<GestationHealthLog> getGestationHealthLogById(Long id) {
        return gestationHealthLogPersistencePort.getById(id);
    }

    @Override
    public List<GestationHealthLog> getGestationHealthLogsByGestationId(Long gestationId) {
        return gestationHealthLogPersistencePort.getByGestationId(gestationId);
    }

    @Override
    public Optional<GestationHealthLog> getGestationHealthLogByHealthLogId(Long healthLogId) {
        return gestationHealthLogPersistencePort.getByHealthLogId(healthLogId);
    }

    @Override
    public HealthLog updateHealthLog(Long id, HealthLog healthLog) {
        healthLog.setId(id);
        return healthLogPersistencePort.save(healthLog);
    }

    @Override
    public KittenHealthLog updateKittenHealthLog(Long id, KittenHealthLog kittenHealthLog) {
        kittenHealthLog.setId(id);
        return kittenHealthLogPersistencePort.save(kittenHealthLog);
    }

    @Override
    public GestationHealthLog updateGestationHealthLog(Long id, GestationHealthLog gestationHealthLog) {
        gestationHealthLog.setId(id);
        return gestationHealthLogPersistencePort.save(gestationHealthLog);
    }

    @Override
    public void deleteHealthLogById(Long id) {
        healthLogPersistencePort.deleteById(id);
    }

    @Override
    public void deleteKittenHealthLogById(Long id) {
        kittenHealthLogPersistencePort.deleteById(id);
    }

    @Override
    public void deleteGestationHealthLogById(Long id) {
        gestationHealthLogPersistencePort.deleteById(id);
    }
}