package projet.uf.modules.health.application.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateGestationHealthLogCommand {
    @NotNull
    private Long gestationId;
    
    @NotNull
    private Long healthLogId;
    
    private BigDecimal weight;
    private BigDecimal temperature;
    private String behavior;
    private String notes;
    private String mammaryObservations;
    private Boolean kittenMovement;
}