package projet.uf.modules.notification.adapter.out.persistence;

import lombok.AllArgsConstructor;
import projet.uf.modules.notification.application.port.out.NotificationPersistencePort;
import projet.uf.modules.notification.domain.UserNotificationPreference;

import java.util.Optional;

@AllArgsConstructor
public class NotificationPersistenceAdapter implements NotificationPersistencePort {
    private final JpaUserNotificationPreferenceRepository userNotificationPreferenceRepository;

    @Override
    public Optional<UserNotificationPreference> getUserNotificationPreferenceByUserId(Long id) {
        return userNotificationPreferenceRepository.findByUserId(id).map(UserNotificationPreferenceEntity::toModel);
    }

    @Override
    public UserNotificationPreference save(UserNotificationPreference userNotificationPreference) {
        UserNotificationPreferenceEntity entity = UserNotificationPreferenceEntity.toEntity(userNotificationPreference);
        UserNotificationPreferenceEntity saved = userNotificationPreferenceRepository.save(entity);
        return UserNotificationPreferenceEntity.toModel(saved);
    }
}
