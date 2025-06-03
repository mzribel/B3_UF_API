package projet.uf.modules.notification.adapter.out.persistence.notif_scheduled;

import jakarta.persistence.*;
import lombok.*;
import projet.uf.modules.notification.domain.NotifScheduled;

import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notif_scheduled")
public class NotifScheduledEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name="notif_type_id", nullable = false)
    private Long notifTypeId;

    @Column
    private String title;

    @Column
    private String description;

    @Column(nullable = false, name = "scheduled_at")
    private LocalDateTime scheduledAt;

    @Column
    private boolean sent;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    public static NotifScheduled toModel(NotifScheduledEntity entity) {
        return new NotifScheduled(
                entity.id,
                entity.userId,
                entity.notifTypeId,
                entity.title,
                entity.description,
                entity.scheduledAt,
                entity.sent,
                entity.sentAt
        );
    }
    public static NotifScheduledEntity toEntity(NotifScheduled model) {
        return new NotifScheduledEntity(
                model.getId(),
                model.getUserId(),
                model.getNotifTypeId(),
                model.getTitle(),
                model.getDescription(),
                model.getScheduledAt(),
                model.isSent(),
                model.getSentAt()
        );
    }
}
