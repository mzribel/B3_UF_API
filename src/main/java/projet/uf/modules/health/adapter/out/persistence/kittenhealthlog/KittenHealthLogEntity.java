package projet.uf.modules.health.adapter.out.persistence.kittenhealthlog;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projet.uf.modules.health.domain.model.KittenHealthLog;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "kitten_health_logs")
public class KittenHealthLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "health_log_id", nullable = false)
    private Long healthLogId;

    @Column(name = "open_eyes_date")
    private LocalDateTime openEyesDate;

    public static KittenHealthLog toModel(KittenHealthLogEntity entity) {
        return KittenHealthLog.builder()
                .id(entity.getId())
                .healthLogId(entity.getHealthLogId())
                .openEyesDate(entity.getOpenEyesDate())
                .build();
    }

    public static KittenHealthLogEntity toEntity(KittenHealthLog model) {
        return new KittenHealthLogEntity(
                model.getId(),
                model.getHealthLogId(),
                model.getOpenEyesDate()
        );
    }
}