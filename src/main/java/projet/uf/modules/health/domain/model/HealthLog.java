package projet.uf.modules.health.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    public HealthLog(Long catId, BigDecimal weightInGrams, BigDecimal temperatureInCelsius,
                    String appetite, String hydratation, String behavior,
                    String stoolQuality, String urineObservations, String notes, LocalDateTime date) {
        this.catId = catId;
        this.weightInGrams = weightInGrams;
        this.temperatureInCelsius = temperatureInCelsius;
        this.appetite = appetite;
        this.hydratation = hydratation;
        this.behavior = behavior;
        this.stoolQuality = stoolQuality;
        this.urineObservations = urineObservations;
        this.notes = notes;
        this.date = date != null ? date : LocalDateTime.now();
    }
}