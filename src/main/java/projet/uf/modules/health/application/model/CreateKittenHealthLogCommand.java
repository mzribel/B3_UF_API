package projet.uf.modules.health.application.model;

import jakarta.validation.constraints.NotNull;
import projet.uf.modules.health.domain.model.KittenHealthLog;

import java.time.LocalDateTime;

public record CreateKittenHealthLogCommand(
        LocalDateTime openEyesDate,
        LocalDateTime firstWalkDate
    )
{
    public KittenHealthLog toKittenHealthLog(@NotNull Long healthLogId) {
        return new KittenHealthLog(healthLogId, openEyesDate, firstWalkDate);
    }
}