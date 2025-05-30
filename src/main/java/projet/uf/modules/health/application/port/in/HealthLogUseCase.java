package projet.uf.modules.health.application.port.in;

import projet.uf.modules.health.domain.model.HealthLog;
import projet.uf.modules.health.domain.model.KittenHealthLog;
import projet.uf.modules.health.domain.model.GestationHealthLog;

import java.util.List;
import java.util.Optional;

public interface HealthLogUseCase {
    // CREATE
    HealthLog createHealthLog(HealthLog healthLog);
    KittenHealthLog createKittenHealthLog(KittenHealthLog kittenHealthLog);
    GestationHealthLog createGestationHealthLog(GestationHealthLog gestationHealthLog);

    // READ
    Optional<HealthLog> getHealthLogById(Long id);
    List<HealthLog> getAllHealthLogs();
    List<HealthLog> getHealthLogsByCatId(Long catId);
    
    Optional<KittenHealthLog> getKittenHealthLogById(Long id);
    Optional<KittenHealthLog> getKittenHealthLogByHealthLogId(Long healthLogId);
    
    Optional<GestationHealthLog> getGestationHealthLogById(Long id);
    List<GestationHealthLog> getGestationHealthLogsByGestationId(Long gestationId);
    Optional<GestationHealthLog> getGestationHealthLogByHealthLogId(Long healthLogId);

    // UPDATE
    HealthLog updateHealthLog(Long id, HealthLog healthLog);
    KittenHealthLog updateKittenHealthLog(Long id, KittenHealthLog kittenHealthLog);
    GestationHealthLog updateGestationHealthLog(Long id, GestationHealthLog gestationHealthLog);

    // DELETE
    void deleteHealthLogById(Long id);
    void deleteKittenHealthLogById(Long id);
    void deleteGestationHealthLogById(Long id);
}