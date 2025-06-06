package projet.uf.modules.notification.adapter.out.persistence.notif_type;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notif_types")
public class NotifTypeEntity {
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
