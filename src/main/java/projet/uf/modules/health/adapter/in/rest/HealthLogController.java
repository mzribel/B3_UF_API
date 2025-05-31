package projet.uf.modules.health.adapter.in.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import projet.uf.modules.auth.adapters.in.rest.security.CurrentUserProvider;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.health.application.model.CreateGestationHealthLogCommand;
import projet.uf.modules.health.application.model.CreateHealthLogCommand;
import projet.uf.modules.health.application.model.CreateKittenHealthLogCommand;
import projet.uf.modules.health.application.port.in.HealthLogUseCase;
import projet.uf.modules.health.domain.model.GestationHealthLog;
import projet.uf.modules.health.domain.model.HealthLog;
import projet.uf.modules.health.domain.model.KittenHealthLog;

import java.util.List;

@RestController
@AllArgsConstructor
public class HealthLogController {
    private final HealthLogUseCase healthLogUseCase;
    private final CurrentUserProvider currentUserProvider;

    // HEALTH LOGS

    @GetMapping("/health-logs")
    public List<HealthLog> getAllHealthLogs() {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return healthLogUseCase.getAllHealthLogs(operator);
    }

    @GetMapping("/health-logs/{healthLogId}")
    public HealthLog getHealthLog(
            @PathVariable Long healthLogId
    ) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return healthLogUseCase.getHealthLogById(healthLogId, operator);
    }

    @PutMapping("/health-logs/{healthLogId}")
    public HealthLog updateHealthLog(
            @PathVariable Long healthLogId,
            @Valid @RequestBody CreateHealthLogCommand command
    ) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return healthLogUseCase.updateHealthLog(healthLogId, command, operator);
    }

    @DeleteMapping("/health-logs/{healthLogId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHealthLog(
            @PathVariable Long healthLogId
    ) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        healthLogUseCase.deleteHealthLogById(healthLogId, operator);
    }

    @GetMapping("/cats/{catId}/health-logs")
    public List<HealthLog> getHealthLogsByCat(@PathVariable Long catId) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return healthLogUseCase.getHealthLogsByCatId(catId, operator);
    }
    @PostMapping("/cats/{catId}/health-logs")
    @ResponseStatus(HttpStatus.CREATED)
    public HealthLog createHealthLogForCat(
            @PathVariable Long catId,
            @RequestBody @Valid CreateHealthLogCommand command) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return healthLogUseCase.createHealthLog(catId, command, operator);
    }

    // GESTATION LOGS

    @PostMapping("/health-logs/{healthLogId}/gestation")
    @ResponseStatus(HttpStatus.CREATED)
    public GestationHealthLog createGestationHealthLog(
            @PathVariable Long healthLogId,
            @RequestBody @Valid CreateGestationHealthLogCommand command
    ) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return healthLogUseCase.createGestationHealthLog(healthLogId, command, operator);
    }

    @PutMapping("/health-logs/{healthLogId}/gestation")
    public GestationHealthLog updateGestationHealthLog(
            @PathVariable Long healthLogId,
            @RequestBody @Valid CreateGestationHealthLogCommand command
    ) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return healthLogUseCase.updateGestationHealthLog(healthLogId, command, operator);
    }

    @DeleteMapping("/health-logs/{healthLogId}/gestation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGestationHealthLog(
            @PathVariable Long healthLogId
    ) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        healthLogUseCase.deleteGestationHealthLogById(healthLogId, operator);
    }

    // KITTEN LOGS

    @PostMapping("/health-logs/{healthLogId}/kitten")
    @ResponseStatus(HttpStatus.CREATED)
    public KittenHealthLog createKittenHealthLog(
            @PathVariable Long healthLogId,
            @RequestBody @Valid CreateKittenHealthLogCommand command
    ) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return healthLogUseCase.createKittenHealthLog(healthLogId, command, operator);
    }

    @PutMapping("/health-logs/{healthLogId}/kitten")
    public KittenHealthLog updateGestationHealthLog(
            @PathVariable Long healthLogId,
            @RequestBody @Valid CreateKittenHealthLogCommand command
    ) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return healthLogUseCase.updateKittenHealthLog(healthLogId, command, operator);
    }

    @DeleteMapping("/health-logs/{healthLogId}/kitten")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteKittenHealthLog(
            @PathVariable Long healthLogId
    ) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        healthLogUseCase.deleteKittenHealthLogById(healthLogId, operator);
    }
}
