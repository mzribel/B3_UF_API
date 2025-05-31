package projet.uf.modules.health.application.model;

import projet.uf.modules.health.domain.model.HealthLog;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateHealthLogCommand(
        BigDecimal weightInGrams,
        BigDecimal temperatureInCelsius,
        String appetite,
        String hydratation,
        String behavior,
        String stoolQuality,
        String urineObservations,
        String notes,
        LocalDateTime date
) {
    public HealthLog toHealthLog(Long catId) {
        LocalDateTime logDate = date == null ? LocalDateTime.now() : date;
        return new HealthLog(
                catId,
                this.weightInGrams,
                this.temperatureInCelsius,
                this.appetite,
                this.hydratation,
                this.behavior,
                this.stoolQuality,
                this.urineObservations,
                this.notes,
                this.date
        );
    }
}