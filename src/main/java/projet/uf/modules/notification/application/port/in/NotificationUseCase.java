package projet.uf.modules.notification.application.port.in;

import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.notification.domain.NotifScheduled;

import java.util.List;

public interface NotificationUseCase {
    void registerFcmToken(FcmTokenCommand command, OperatorUser operatorUser);
    void sendNotificationToUser(NotificationCommand command, Long userId, OperatorUser operator);
    NotifScheduled registerScheduledNotification(ScheduledNotificationCommand command, Long userId, OperatorUser operator);
    List<NotifScheduled> getScheduledNotificationsForUser(Long userId, OperatorUser operator);
}
