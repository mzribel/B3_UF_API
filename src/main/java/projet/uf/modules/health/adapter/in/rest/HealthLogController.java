package projet.uf.modules.health.adapter.in.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import projet.uf.modules.health.application.model.CreateGestationHealthLogCommand;
import projet.uf.modules.health.application.model.CreateHealthLogCommand;
import projet.uf.modules.health.application.model.CreateKittenHealthLogCommand;
import projet.uf.modules.health.application.model.HealthLogCommandMapper;
import projet.uf.modules.health.application.port.in.HealthLogUseCase;
import projet.uf.modules.health.domain.model.GestationHealthLog;
import projet.uf.modules.health.domain.model.HealthLog;
import projet.uf.modules.health.domain.model.KittenHealthLog;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class HealthLogController {
    private final HealthLogUseCase healthLogUseCase;

    // HealthLog endpoints
    @GetMapping({"/health-logs/", "/health-logs"})
    public List<HealthLog> getAllHealthLogs() {
        return healthLogUseCase.getAllHealthLogs();
    }

    @GetMapping({"/health-logs/{id}/", "/health-logs/{id}"})
    public Optional<HealthLog> getHealthLogById(@PathVariable Long id) {
        return healthLogUseCase.getHealthLogById(id);
    }

    @GetMapping({"/cats/{catId}/health-logs/", "/cats/{catId}/health-logs"})
    public List<HealthLog> getHealthLogsByCatId(@PathVariable Long catId) {
        return healthLogUseCase.getHealthLogsByCatId(catId);
    }

    @PostMapping({"/health-logs/", "/health-logs"})
    @ResponseStatus(HttpStatus.CREATED)
    public HealthLog createHealthLog(@RequestBody @Valid CreateHealthLogCommand command) {
        HealthLog healthLog = HealthLogCommandMapper.fromCreateHealthLogCommand(command);
        return healthLogUseCase.createHealthLog(healthLog);
    }

    @PutMapping({"/health-logs/{id}/", "/health-logs/{id}"})
    public HealthLog updateHealthLog(@PathVariable Long id, @RequestBody @Valid CreateHealthLogCommand command) {
        HealthLog healthLog = HealthLogCommandMapper.fromCreateHealthLogCommand(command);
        return healthLogUseCase.updateHealthLog(id, healthLog);
    }

    @DeleteMapping({"/health-logs/{id}/", "/health-logs/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHealthLog(@PathVariable Long id) {
        healthLogUseCase.deleteHealthLogById(id);
    }

    // KittenHealthLog endpoints
    @GetMapping({"/kitten-health-logs/{id}/", "/kitten-health-logs/{id}"})
    public Optional<KittenHealthLog> getKittenHealthLogById(@PathVariable Long id) {
        return healthLogUseCase.getKittenHealthLogById(id);
    }

    @GetMapping({"/health-logs/{healthLogId}/kitten-health-log/", "/health-logs/{healthLogId}/kitten-health-log"})
    public Optional<KittenHealthLog> getKittenHealthLogByHealthLogId(@PathVariable Long healthLogId) {
        return healthLogUseCase.getKittenHealthLogByHealthLogId(healthLogId);
    }

    @PostMapping({"/kitten-health-logs/", "/kitten-health-logs"})
    @ResponseStatus(HttpStatus.CREATED)
    public KittenHealthLog createKittenHealthLog(@RequestBody @Valid CreateKittenHealthLogCommand command) {
        KittenHealthLog kittenHealthLog = HealthLogCommandMapper.fromCreateKittenHealthLogCommand(command);
        return healthLogUseCase.createKittenHealthLog(kittenHealthLog);
    }

    @PutMapping({"/kitten-health-logs/{id}/", "/kitten-health-logs/{id}"})
    public KittenHealthLog updateKittenHealthLog(@PathVariable Long id, @RequestBody @Valid CreateKittenHealthLogCommand command) {
        KittenHealthLog kittenHealthLog = HealthLogCommandMapper.fromCreateKittenHealthLogCommand(command);
        return healthLogUseCase.updateKittenHealthLog(id, kittenHealthLog);
    }

    @DeleteMapping({"/kitten-health-logs/{id}/", "/kitten-health-logs/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteKittenHealthLog(@PathVariable Long id) {
        healthLogUseCase.deleteKittenHealthLogById(id);
    }

    // GestationHealthLog endpoints
    @GetMapping({"/gestation-health-logs/{id}/", "/gestation-health-logs/{id}"})
    public Optional<GestationHealthLog> getGestationHealthLogById(@PathVariable Long id) {
        return healthLogUseCase.getGestationHealthLogById(id);
    }

    @GetMapping({"/gestations/{gestationId}/health-logs/", "/gestations/{gestationId}/health-logs"})
    public List<GestationHealthLog> getGestationHealthLogsByGestationId(@PathVariable Long gestationId) {
        return healthLogUseCase.getGestationHealthLogsByGestationId(gestationId);
    }

    @GetMapping({"/health-logs/{healthLogId}/gestation-health-log/", "/health-logs/{healthLogId}/gestation-health-log"})
    public Optional<GestationHealthLog> getGestationHealthLogByHealthLogId(@PathVariable Long healthLogId) {
        return healthLogUseCase.getGestationHealthLogByHealthLogId(healthLogId);
    }

    @PostMapping({"/gestation-health-logs/", "/gestation-health-logs"})
    @ResponseStatus(HttpStatus.CREATED)
    public GestationHealthLog createGestationHealthLog(@RequestBody @Valid CreateGestationHealthLogCommand command) {
        GestationHealthLog gestationHealthLog = HealthLogCommandMapper.fromCreateGestationHealthLogCommand(command);
        return healthLogUseCase.createGestationHealthLog(gestationHealthLog);
    }

    @PutMapping({"/gestation-health-logs/{id}/", "/gestation-health-logs/{id}"})
    public GestationHealthLog updateGestationHealthLog(@PathVariable Long id, @RequestBody @Valid CreateGestationHealthLogCommand command) {
        GestationHealthLog gestationHealthLog = HealthLogCommandMapper.fromCreateGestationHealthLogCommand(command);
        return healthLogUseCase.updateGestationHealthLog(id, gestationHealthLog);
    }

    @DeleteMapping({"/gestation-health-logs/{id}/", "/gestation-health-logs/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGestationHealthLog(@PathVariable Long id) {
        healthLogUseCase.deleteGestationHealthLogById(id);
    }
}
