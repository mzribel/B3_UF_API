package projet.uf.modules.health.domain.model;

import lombok.*;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GestationHealthLog {
    private Long id;
    private Long gestationId;
    private Long healthLogId;
    private String mammaryObservations;
    private Boolean kittenMovement;

    public GestationHealthLog(Long gestationId, Long healthLogId, String mammaryObservations, Boolean kittenMovement) {
        this.gestationId = gestationId;
        this.healthLogId = healthLogId;
        this.mammaryObservations = mammaryObservations;
        this.kittenMovement = kittenMovement;
    }
}