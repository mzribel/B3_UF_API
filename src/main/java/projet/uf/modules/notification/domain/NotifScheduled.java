package projet.uf.modules.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class NotifScheduled {
    private Long id;
    private Long userId;
    private Long notifTypeId;
    private String title;
    private String description;
    private LocalDateTime scheduledAt;
    private boolean sent;
    private LocalDateTime sentAt;

    public NotifScheduled(Long userId, Long notifTypeId, String title, String description, LocalDateTime scheduledAt) {
        this.userId = userId;
        this.notifTypeId = notifTypeId;
        this.title = title;
        this.description = description;
        this.scheduledAt = scheduledAt;
    }
}
