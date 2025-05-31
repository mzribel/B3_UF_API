package projet.uf.modules.health.application;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.cat.application.ports.in.CatAccessUseCase;
import projet.uf.modules.health.application.command.CreateGestationHealthLogCommand;
import projet.uf.modules.health.application.command.CreateHealthLogCommand;
import projet.uf.modules.health.application.command.CreateKittenHealthLogCommand;
import projet.uf.modules.health.application.port.in.HealthLogAccessUseCase;
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
    private final HealthLogAccessUseCase healthLogAccessUseCase;

    @Override
    public HealthLog createHealthLog(Long catId, CreateHealthLogCommand command, OperatorUser operatorUser) {
        catAccessUseCase.getCatOrThrow(catId, operatorUser);
        HealthLog log = command.toHealthLog(catId);
        return healthLogPersistencePort.save(log);
    }

    @Override
    public KittenHealthLog createKittenHealthLog(Long healthLogId, CreateKittenHealthLogCommand command, OperatorUser operatorUser) {
        healthLogAccessUseCase.getHealthLogOrThrow(healthLogId, operatorUser);
        KittenHealthLog kittenLog = command.toKittenHealthLog(healthLogId);
        return kittenHealthLogPersistencePort.save(kittenLog);
    }

    @Override
    public GestationHealthLog createGestationHealthLog(Long healthLogId, CreateGestationHealthLogCommand command, OperatorUser operatorUser) {
        healthLogAccessUseCase.getHealthLogOrThrow(healthLogId, operatorUser);
        GestationHealthLog log = command.toModel(healthLogId);
        return gestationHealthLogPersistencePort.save(log);
    }

    @Override
    public HealthLog getHealthLogById(Long healthLogId, OperatorUser operatorUser) {
        return healthLogAccessUseCase.getHealthLogOrThrow(healthLogId, operatorUser);
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
        HealthLog healthLog = healthLogAccessUseCase.getHealthLogOrThrow(healthLogId, operatorUser);
        HealthLog updatedLog = command.toHealthLog(healthLog.getCatId());
        updatedLog.setId(healthLogId);
        return healthLogPersistencePort.save(updatedLog);
    }

    @Override
    public KittenHealthLog updateKittenHealthLog(Long healthLogId, CreateKittenHealthLogCommand command, OperatorUser operatorUser) {
        if (!healthLogAccessUseCase.hasUserAccessToHealthLog(healthLogId, operatorUser)) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }
        KittenHealthLog existing = kittenHealthLogPersistencePort.getByHealthLogId(healthLogId)
                .orElseThrow(() -> new ApiException("Entrée de chaton non initialisée", HttpStatus.BAD_REQUEST));

        KittenHealthLog updated = command.toKittenHealthLog(healthLogId);
        updated.setId(existing.getId());
        return kittenHealthLogPersistencePort.save(updated);
    }

    @Override
    public GestationHealthLog updateGestationHealthLog(Long healthLogId, CreateGestationHealthLogCommand command, OperatorUser operatorUser) {
        if (!healthLogAccessUseCase.hasUserAccessToHealthLog(healthLogId, operatorUser)) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }
        GestationHealthLog existing = gestationHealthLogPersistencePort.getByHealthLogId(healthLogId)
                .orElseThrow(() -> new ApiException("Entrée de gestation non initialisée", HttpStatus.BAD_REQUEST));

        GestationHealthLog updated = command.toModel(healthLogId);
        updated.setId(existing.getId());
        updated.setGestationId(existing.getGestationId());
        return gestationHealthLogPersistencePort.save(updated);
    }

    @Override
    public void deleteHealthLogById(Long healthLogId, OperatorUser operatorUser) {
        if (healthLogPersistencePort.getById(healthLogId).isEmpty()) {
            throw new ApiException("Entrée de santé introuvable", HttpStatus.NOT_FOUND);
        }

        if (!healthLogAccessUseCase.hasUserAccessToHealthLog(healthLogId, operatorUser)) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }

        healthLogPersistencePort.deleteById(healthLogId);
    }

    @Override
    public void deleteKittenHealthLogById(Long healthLogId, OperatorUser operatorUser) {
        if (!healthLogAccessUseCase.hasUserAccessToHealthLog(healthLogId, operatorUser)) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }

        Long kittenLogId = kittenHealthLogPersistencePort.getByHealthLogId(healthLogId)
                .orElseThrow(() -> new ApiException("Entrée de santé chaton introuvable", HttpStatus.NOT_FOUND))
                .getHealthLogId();

        kittenHealthLogPersistencePort.deleteById(kittenLogId);
    }

    @Override
    public void deleteGestationHealthLogById(Long healthLogId, OperatorUser operatorUser) {
        if (!healthLogAccessUseCase.hasUserAccessToHealthLog(healthLogId, operatorUser)) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }

        Long gestationLogId = gestationHealthLogPersistencePort.getByHealthLogId(healthLogId)
                .orElseThrow(() -> new ApiException("Entrée de santé gestation introuvable", HttpStatus.NOT_FOUND))
                .getHealthLogId();

        gestationHealthLogPersistencePort.deleteById(gestationLogId);
    }
}