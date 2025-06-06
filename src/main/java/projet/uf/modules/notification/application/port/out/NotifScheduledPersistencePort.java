package projet.uf.modules.notification.application.port.out;

import projet.uf.modules.notification.domain.NotifScheduled;
import java.util.List;

public interface NotifScheduledPersistencePort {
    List<NotifScheduled> getDueScheduledNotifications();
    void markSent(Long id);
    NotifScheduled save(NotifScheduled notifScheduled);
    List<NotifScheduled> getScheduledNotificationsByUserId(Long id);
}
