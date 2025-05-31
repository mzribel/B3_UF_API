package projet.uf.modules.health.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class KittenHealthLog {
    private Long id;
    private Long healthLogId;
    private LocalDateTime openEyesDate;

    public KittenHealthLog(Long healthLogId, LocalDateTime openEyesDate) {
        this.healthLogId = healthLogId;
        this.openEyesDate = openEyesDate;
    }
}