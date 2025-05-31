package projet.uf.modules.health.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KittenHealthLog {
    private Long id;
    private Long healthLogId;
    private LocalDateTime openEyesDate;

    public KittenHealthLog(Long healthLogId, LocalDateTime openEyesDate) {
        this.healthLogId = healthLogId;
        this.openEyesDate = openEyesDate;
    }
}