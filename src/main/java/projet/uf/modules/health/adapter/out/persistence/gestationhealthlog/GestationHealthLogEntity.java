package projet.uf.modules.health.adapter.out.persistence.gestationhealthlog;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}