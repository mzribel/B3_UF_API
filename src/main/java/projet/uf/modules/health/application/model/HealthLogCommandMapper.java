package projet.uf.modules.health.application.model;

import projet.uf.modules.health.domain.model.GestationHealthLog;
import projet.uf.modules.health.domain.model.HealthLog;
import projet.uf.modules.health.domain.model.KittenHealthLog;

import java.time.LocalDateTime;

public class HealthLogCommandMapper {
    
    public static HealthLog fromCreateHealthLogCommand(CreateHealthLogCommand command) {
        return new HealthLog(
                command.getCatId(),
                command.getWeightInGrams(),
                command.getTemperatureInCelsius(),
                command.getAppetite(),
                command.getHydratation(),
                command.getBehavior(),
                command.getStoolQuality(),
                command.getUrineObservations(),
                command.getNotes()
        );
    }
    
    public static KittenHealthLog fromCreateKittenHealthLogCommand(CreateKittenHealthLogCommand command) {
        return new KittenHealthLog(
                command.getHealthLogId(),
                command.getOpenEyesDate(),
                command.getFirstWakDate()
        );
    }
    
    public static GestationHealthLog fromCreateGestationHealthLogCommand(CreateGestationHealthLogCommand command) {
        return new GestationHealthLog(
                command.getGestationId(),
                command.getHealthLogId(),
                command.getWeight(),
                command.getTemperature(),
                command.getBehavior(),
                command.getNotes(),
                command.getMammaryObservations(),
                command.getKittenMovement()
        );
    }
}