package projet.uf.modules.health.application.command;

import jakarta.validation.constraints.NotNull;
import projet.uf.modules.health.domain.model.GestationHealthLog;

public record CreateGestationHealthLogCommand(
        String mammaryObservations,
        Boolean kittenMovement
)
{
        public GestationHealthLog toModel(
                @NotNull Long healthLogId
        ) {
                return new GestationHealthLog(
                        null,
                        healthLogId,
                        this.mammaryObservations,
                        this.kittenMovement
                );
        }
}