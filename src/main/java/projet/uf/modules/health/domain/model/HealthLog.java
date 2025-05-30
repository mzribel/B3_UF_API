package projet.uf.modules.health.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class HealthLog {
    private Long id;
    private Long catId;
    private BigDecimal weightInGrams;
    private BigDecimal temperatureInCelsius;
    private String appetite;
    private String hydratation;
    private String behavior;
    private String stoolQuality;
    private String urineObservations;
    private String notes;
    private LocalDateTime date;

    public HealthLog() {
        this.date = LocalDateTime.now();
    }

    public HealthLog(Long catId, BigDecimal weightInGrams, BigDecimal temperatureInCelsius, 
                    String appetite, String hydratation, String behavior, 
                    String stoolQuality, String urineObservations, String notes) {
        this.catId = catId;
        this.weightInGrams = weightInGrams;
        this.temperatureInCelsius = temperatureInCelsius;
        this.appetite = appetite;
        this.hydratation = hydratation;
        this.behavior = behavior;
        this.stoolQuality = stoolQuality;
        this.urineObservations = urineObservations;
        this.notes = notes;
        this.date = LocalDateTime.now();
    }
}