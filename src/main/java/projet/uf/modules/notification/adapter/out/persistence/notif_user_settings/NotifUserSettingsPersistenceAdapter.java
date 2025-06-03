package projet.uf.modules.notification.adapter.out.persistence.notif_user_settings;

import lombok.AllArgsConstructor;
import projet.uf.modules.notification.application.port.out.NotifUserSettingsPersistencePort;
import projet.uf.modules.notification.domain.NotifUserSettings;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
public class NotifUserSettingsPersistenceAdapter implements NotifUserSettingsPersistencePort {
    private final JpaNotifUserSettingsRepository jpaTokenRepository;

    public Optional<NotifUserSettings> getSettingsByUserId(Long userId) {
        return jpaTokenRepository.findByUserId(userId).map(NotifUserSettingsEntity::toModel);
    }

    @Override
    public NotifUserSettings save(NotifUserSettings notifUserSettings) {
        NotifUserSettingsEntity entity = NotifUserSettingsEntity.toEntity(notifUserSettings);
        entity.setLastUpdated(LocalDateTime.now());
        NotifUserSettingsEntity saved = jpaTokenRepository.save(entity);
        return NotifUserSettingsEntity.toModel(saved);
    }
}
