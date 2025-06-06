package projet.uf.modules.notification.application.port.out;

import projet.uf.modules.notification.domain.NotifUserSettings;

import java.util.Optional;

public interface NotifUserSettingsPersistencePort {
    Optional<NotifUserSettings> getSettingsByUserId(Long userId);
    NotifUserSettings save(NotifUserSettings notifUserSettings);
}
