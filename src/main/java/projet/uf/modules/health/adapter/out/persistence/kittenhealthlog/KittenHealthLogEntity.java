package projet.uf.modules.health.adapter.out.persistence.kittenhealthlog;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "first_wak_date")
    private LocalDateTime firstWakDate;
}