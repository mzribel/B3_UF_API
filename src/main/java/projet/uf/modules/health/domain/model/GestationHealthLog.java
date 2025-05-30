package projet.uf.modules.health.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class GestationHealthLog {
    private Long id;
    private Long gestationId;
    private Long healthLogId;
    private BigDecimal weight;
    private BigDecimal temperature;
    private String behavior;
    private String notes;
    private String mammaryObservations;
    private Boolean kittenMovement;

    public GestationHealthLog() {
    }

    public GestationHealthLog(Long gestationId, Long healthLogId, BigDecimal weight, BigDecimal temperature,
                             String behavior, String notes, String mammaryObservations, Boolean kittenMovement) {
        this.gestationId = gestationId;
        this.healthLogId = healthLogId;
        this.weight = weight;
        this.temperature = temperature;
        this.behavior = behavior;
        this.notes = notes;
        this.mammaryObservations = mammaryObservations;
        this.kittenMovement = kittenMovement;
    }
}