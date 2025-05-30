package projet.uf.modules.health.application.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateKittenHealthLogCommand {
    @NotNull
    private Long healthLogId;
    
    private LocalDateTime openEyesDate;
    private LocalDateTime firstWakDate;
}