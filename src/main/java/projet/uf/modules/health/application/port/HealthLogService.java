package projet.uf.modules.health.application.port;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.cat.application.ports.in.CatAccessUseCase;
import projet.uf.modules.cat.domain.model.Cat;
import projet.uf.modules.health.application.model.CreateGestationHealthLogCommand;
import projet.uf.modules.health.application.model.CreateHealthLogCommand;
import projet.uf.modules.health.application.model.CreateKittenHealthLogCommand;
import projet.uf.modules.health.application.port.in.HealthLogUseCase;
import projet.uf.modules.health.application.port.out.GestationHealthLogPersistencePort;
import projet.uf.modules.health.application.port.out.HealthLogPersistencePort;
import projet.uf.modules.health.application.port.out.KittenHealthLogPersistencePort;
import projet.uf.modules.health.domain.model.GestationHealthLog;
import projet.uf.modules.health.domain.model.HealthLog;
import projet.uf.modules.health.domain.model.KittenHealthLog;

import java.util.List;

@AllArgsConstructor
public class HealthLogService implements HealthLogUseCase {
    private final HealthLogPersistencePort healthLogPersistencePort;
    private final KittenHealthLogPersistencePort kittenHealthLogPersistencePort;
    private final GestationHealthLogPersistencePort gestationHealthLogPersistencePort;
    private final CatAccessUseCase catAccessUseCase;

    @Override
    public HealthLog createHealthLog(Long catId, CreateHealthLogCommand command, OperatorUser operatorUser) {
        catAccessUseCase.getCatOrThrow(catId, operatorUser);
        HealthLog log = command.toHealthLog(catId);
        return healthLogPersistencePort.save(log);
    }

    @Override
    public KittenHealthLog createKittenHealthLog(Long healthLogId, CreateKittenHealthLogCommand command, OperatorUser operatorUser) {
        HealthLog healthLog = healthLogPersistencePort.getById(healthLogId)
                .orElseThrow(() -> new ApiException("Entrée de santé introuvable", HttpStatus.BAD_REQUEST));

        if (catAccessUseCase.hasUserAccessToCat(healthLog.getCatId(), operatorUser)) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }

        KittenHealthLog log = command.toKittenHealthLog(healthLogId);
        return kittenHealthLogPersistencePort.save(log);
    }

    @Override
    public GestationHealthLog createGestationHealthLog(Long healthLogId, CreateGestationHealthLogCommand command, OperatorUser operatorUser) {
        HealthLog healthLog = healthLogPersistencePort.getById(healthLogId)
                .orElseThrow(() -> new ApiException("Entrée de santé introuvable", HttpStatus.BAD_REQUEST));

        if (catAccessUseCase.hasUserAccessToCat(healthLog.getCatId(), operatorUser)) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }

        GestationHealthLog log = command.toModel(healthLogId, 1L);
        return gestationHealthLogPersistencePort.save(log);
    }

    @Override
    public HealthLog getHealthLogById(Long healthLogId, OperatorUser operatorUser) {
        HealthLog healthLog = healthLogPersistencePort.getById(healthLogId)
                .orElseThrow(() -> new ApiException("Entrée de santé introuvable", HttpStatus.BAD_REQUEST));

        if (catAccessUseCase.hasUserAccessToCat(healthLog.getCatId(), operatorUser)) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }

        return healthLog;
    }

    @Override
    public List<HealthLog> getAllHealthLogs(OperatorUser operatorUser) {
        if (!operatorUser.isAdmin()) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }
        return healthLogPersistencePort.getAll();
    }

    @Override
    public List<HealthLog> getHealthLogsByCatId(Long catId, OperatorUser operatorUser) {
        if (!catAccessUseCase.hasUserAccessToCat(catId, operatorUser)) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }
        return healthLogPersistencePort.getByCatId(catId);
    }

    @Override
    public HealthLog updateHealthLog(Long healthLogId, CreateHealthLogCommand command, OperatorUser operatorUser) {
        HealthLog healthLog = healthLogPersistencePort.getById(healthLogId)
                .orElseThrow(()-> new ApiException("Entrée de santé introuvable", HttpStatus.BAD_REQUEST));
        if (!catAccessUseCase.hasUserAccessToCat(healthLog.getCatId(), operatorUser)) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }

        HealthLog updatedLog = command.toHealthLog(healthLog.getCatId());
        updatedLog.setId(healthLogId);
        return healthLogPersistencePort.save(updatedLog);
    }

    @Override
    public KittenHealthLog updateKittenHealthLog(Long healthLogId, CreateKittenHealthLogCommand command, OperatorUser operatorUser) {
        return null;
    }

    @Override
    public GestationHealthLog updateGestationHealthLog(Long healthLogId, CreateGestationHealthLogCommand command, OperatorUser operatorUser) {
        return null;
    }

    @Override
    public void deleteHealthLogById(Long healthLogId, OperatorUser operatorUser) {
        HealthLog healthLog = healthLogPersistencePort.getById(healthLogId)
                .orElseThrow(()-> new ApiException("Entrée de santé introuvable", HttpStatus.BAD_REQUEST));
        if (!catAccessUseCase.hasUserAccessToCat(healthLog.getCatId(), operatorUser)) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }
        healthLogPersistencePort.deleteById(healthLogId);
    }

    @Override
    public void deleteKittenHealthLogById(Long healthLogId, OperatorUser operatorUser) {

    }

    @Override
    public void deleteGestationHealthLogById(Long healthLogId, OperatorUser operatorUser) {

    }
}