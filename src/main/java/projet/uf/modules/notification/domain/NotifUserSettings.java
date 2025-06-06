package projet.uf.modules.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class NotifUserSettings {
    private Long id;
    private Long userId;
    private String fcmToken;
    private LocalDateTime lastUpdatedAt;
    private boolean isEnabled = true;

    public NotifUserSettings(Long userId, String fcmToken) {
        this.userId = userId;
        this.fcmToken = fcmToken;
        this.lastUpdatedAt = LocalDateTime.now();
    }
    public NotifUserSettings(Long userId) {
        this.userId = userId;
        this.lastUpdatedAt = LocalDateTime.now();
    }
}
