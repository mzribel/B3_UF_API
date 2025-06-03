package projet.uf.modules.notification.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projet.uf.modules.notification.domain.UserNotificationPreference;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_notification_preferences")
public class UserNotificationPreferenceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "user_id")
    private Long userId;

    @Column(nullable = false, name = "is_push_enabled")
    @Builder.Default
    private boolean isPushEnabled = true;

    @Column(name = "fcm_token")
    private String fcmToken;

    public static UserNotificationPreference toModel(final UserNotificationPreferenceEntity entity) {
        return new UserNotificationPreference(
                entity.id,
                entity.userId,
                entity.isPushEnabled,
                entity.fcmToken
        );
    }

    public static UserNotificationPreferenceEntity toEntity(final UserNotificationPreference model) {
        return new UserNotificationPreferenceEntity(
                model.getId(),
                model.getUserId(),
                model.isPushEnabled(),
                model.getFcmToken()
        );
    }
}
