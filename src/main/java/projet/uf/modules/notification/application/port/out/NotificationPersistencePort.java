package projet.uf.modules.notification.application.port.out;

import projet.uf.modules.notification.domain.UserNotificationPreference;

import java.util.Optional;

public interface NotificationPersistencePort {
    Optional<UserNotificationPreference> getUserNotificationPreferenceByUserId(Long id);
    UserNotificationPreference save(UserNotificationPreference userNotificationPreference);
}
