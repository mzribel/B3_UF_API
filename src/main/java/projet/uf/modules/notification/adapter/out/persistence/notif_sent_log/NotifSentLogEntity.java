package projet.uf.modules.notification.adapter.out.persistence.notif_sent_log;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notif_types")
public class NotifSentLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column
    private String name;

    @Column(nullable = false, name = "is_default_enabled")
    private boolean defaultEnabled = true;
}
