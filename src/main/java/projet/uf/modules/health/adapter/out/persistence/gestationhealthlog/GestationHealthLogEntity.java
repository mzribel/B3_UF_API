package projet.uf.modules.health.adapter.out.persistence.gestationhealthlog;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projet.uf.modules.health.domain.model.GestationHealthLog;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "gestation_health_logs")
public class GestationHealthLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gestation_id", nullable = false)
    private Long gestationId;

    @Column(name = "health_log_id", nullable = false)
    private Long healthLogId;

    @Column
    private BigDecimal weight;

    @Column
    private BigDecimal temperature;

    @Column
    private String behavior;

    @Column
    private String notes;

    @Column(name = "mammary_observations")
    private String mammaryObservations;

    @Column(name = "kitten_movement")
    private Boolean kittenMovement;

    public static GestationHealthLog toModel(GestationHealthLogEntity entity) {
        return GestationHealthLog.builder()
                .id(entity.getId())
                .gestationId(entity.getGestationId())
                .healthLogId(entity.getHealthLogId())
                .weight(entity.getWeight())
                .temperature(entity.getTemperature())
                .behavior(entity.getBehavior())
                .notes(entity.getNotes())
                .mammaryObservations(entity.getMammaryObservations())
                .kittenMovement(entity.getKittenMovement())
                .build();
    }

    public static GestationHealthLogEntity toEntity(GestationHealthLog model) {
        return new GestationHealthLogEntity(
                model.getId(),
                model.getGestationId(),
                model.getHealthLogId(),
                model.getWeight(),
                model.getTemperature(),
                model.getBehavior(),
                model.getNotes(),
                model.getMammaryObservations(),
                model.getKittenMovement()
        );
    }
}