package projet.uf.modules.notification.adapter.out.persistence.notif_user_settings;

import jakarta.persistence.*;
import lombok.*;
import projet.uf.modules.notification.domain.NotifUserSettings;

import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notif_user_settings")
public class NotifUserSettingsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "fcm_token", unique = true, nullable = false)
    private String fcmToken;

    @Column(name = "last_updated", nullable = false)
    @Setter
    private LocalDateTime lastUpdated;

    @Column(name="is_enabled", nullable = false)
    private boolean enabled = true;

    public static NotifUserSettings toModel(NotifUserSettingsEntity entity) {
        return new NotifUserSettings(
                entity.getId(),
                entity.getUserId(),
                entity.fcmToken,
                entity.lastUpdated,
                entity.enabled
        );
    }
    public static NotifUserSettingsEntity toEntity(NotifUserSettings model) {
        return new NotifUserSettingsEntity(
                model.getId(),
                model.getUserId(),
                model.getFcmToken(),
                model.getLastUpdatedAt(),
                model.isEnabled()
        );
    }
}
