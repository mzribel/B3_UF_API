package projet.uf.modules.health.application.port.in;

import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.health.application.model.CreateGestationHealthLogCommand;
import projet.uf.modules.health.application.model.CreateHealthLogCommand;
import projet.uf.modules.health.application.model.CreateKittenHealthLogCommand;
import projet.uf.modules.health.domain.model.HealthLog;
import projet.uf.modules.health.domain.model.KittenHealthLog;
import projet.uf.modules.health.domain.model.GestationHealthLog;

import java.util.List;

public interface HealthLogUseCase {
    // CREATE
    HealthLog createHealthLog(Long catId, CreateHealthLogCommand command, OperatorUser operatorUser);
    KittenHealthLog createKittenHealthLog(Long healthLogId, CreateKittenHealthLogCommand command, OperatorUser operatorUser);
    GestationHealthLog createGestationHealthLog(Long healthLogId, CreateGestationHealthLogCommand command, OperatorUser operatorUser);

    // READ
    HealthLog getHealthLogById(Long healthLogId, OperatorUser operatorUser);
    List<HealthLog> getAllHealthLogs(OperatorUser operatorUser);
    List<HealthLog> getHealthLogsByCatId(Long catId, OperatorUser operatorUser);

    // UPDATE
    HealthLog updateHealthLog(Long healthLogId, CreateHealthLogCommand command, OperatorUser operatorUser);
    KittenHealthLog updateKittenHealthLog(Long healthLogId, CreateKittenHealthLogCommand command, OperatorUser operatorUser);
    GestationHealthLog updateGestationHealthLog(Long healthLogId, CreateGestationHealthLogCommand command, OperatorUser operatorUser);

    // DELETE
    void deleteHealthLogById(Long healthLogId, OperatorUser operatorUser);
    void deleteKittenHealthLogById(Long healthLogId, OperatorUser operatorUser);
    void deleteGestationHealthLogById(Long healthLogId, OperatorUser operatorUser);
}