package projet.uf.modules.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserNotificationPreference {
    Long id;
    Long userId;
    boolean isPushEnabled;
    String fcmToken;

    public UserNotificationPreference(Long userId, boolean isPushEnabled, String fcmToken) {
        this.userId = userId;
        this.isPushEnabled = isPushEnabled;
        this.fcmToken = fcmToken;
    }
}
