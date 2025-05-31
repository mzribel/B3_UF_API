package projet.uf.modules.health.adapter.out.persistence.healthlog;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projet.uf.modules.health.domain.model.HealthLog;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "health_logs")
public class HealthLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cat_id", nullable = false)
    private Long catId;

    @Column(name = "weight_in_grams")
    private BigDecimal weightInGrams;

    @Column(name = "temperature_in_celsius")
    private BigDecimal temperatureInCelsius;

    @Column
    private String appetite;

    @Column
    private String hydratation;

    @Column
    private String behavior;

    @Column(name = "stool_quality")
    private String stoolQuality;

    @Column(name = "urine_observations")
    private String urineObservations;

    @Column
    private String notes;

    @Column(nullable = false)
    private LocalDateTime date;

    public static HealthLog toModel(HealthLogEntity entity) {
        return HealthLog.builder()
                .id(entity.getId())
                .catId(entity.getCatId())
                .weightInGrams(entity.getWeightInGrams())
                .temperatureInCelsius(entity.getTemperatureInCelsius())
                .appetite(entity.getAppetite())
                .hydratation(entity.getHydratation())
                .behavior(entity.getBehavior())
                .stoolQuality(entity.getStoolQuality())
                .urineObservations(entity.getUrineObservations())
                .notes(entity.getNotes())
                .date(entity.getDate())
                .build();
    }

    public static HealthLogEntity toEntity(HealthLog model) {
        return new HealthLogEntity(
                model.getId(),
                model.getCatId(),
                model.getWeightInGrams(),
                model.getTemperatureInCelsius(),
                model.getAppetite(),
                model.getHydratation(),
                model.getBehavior(),
                model.getStoolQuality(),
                model.getUrineObservations(),
                model.getNotes(),
                model.getDate()
        );
    }
}