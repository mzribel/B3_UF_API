package projet.uf.modules.health.application.model;

import jakarta.validation.constraints.NotNull;
import projet.uf.modules.health.domain.model.GestationHealthLog;

import java.math.BigDecimal;

public record CreateGestationHealthLogCommand(
        BigDecimal weight,
        BigDecimal temperature,
        String behavior,
        String notes,
        String mammaryObservations,
        Boolean kittenMovement
)
{
        public GestationHealthLog toModel(
                @NotNull Long gestationId,
                @NotNull Long healthLogId
        ) {
                return new GestationHealthLog(
                        gestationId,
                        healthLogId,
                        this.weight,
                        this.temperature,
                        this.behavior,
                        this.notes,
                        this.mammaryObservations,
                        this.kittenMovement
                );
        }
}