package projet.uf.modules.notification.adapter.out.persistence.notif_scheduled;

import lombok.AllArgsConstructor;
import projet.uf.modules.notification.application.port.out.NotifScheduledPersistencePort;
import projet.uf.modules.notification.domain.NotifScheduled;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class NotifScheduledPersistenceAdapter implements NotifScheduledPersistencePort {
    private final JpaNotifScheduledRepository scheduledNotificationRepository;

    @Override
    public List<NotifScheduled> getDueScheduledNotifications() {
        return scheduledNotificationRepository.findBySentFalseAndScheduledAtBefore(LocalDateTime.now())
                .stream().map(NotifScheduledEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public void markSent(Long id) {
        NotifScheduledEntity entity = scheduledNotificationRepository.findById(id).orElseThrow();
        entity.setSent(true);
        entity.setSentAt(LocalDateTime.now());
        scheduledNotificationRepository.save(entity);
    }

    @Override
    public NotifScheduled save(NotifScheduled notifScheduled) {
        NotifScheduledEntity entity = NotifScheduledEntity.toEntity(notifScheduled);
        NotifScheduledEntity savedEntity = scheduledNotificationRepository.save(entity);
        return NotifScheduledEntity.toModel(savedEntity);
    }

    @Override
    public List<NotifScheduled> getScheduledNotificationsByUserId(Long id) {
        return scheduledNotificationRepository.findByUserIdAndScheduledAtAfterAndSent(id, LocalDateTime.now(), false)
                .stream().map(NotifScheduledEntity::toModel).collect(Collectors.toList());
    }
}
