package projet.uf.modules.notification.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projet.uf.modules.notification.adapter.out.persistence.notif_scheduled.JpaNotifScheduledRepository;
import projet.uf.modules.notification.adapter.out.persistence.notif_scheduled.NotifScheduledPersistenceAdapter;
import projet.uf.modules.notification.adapter.out.persistence.notif_user_settings.JpaNotifUserSettingsRepository;
import projet.uf.modules.notification.adapter.out.persistence.notif_user_settings.NotifUserSettingsPersistenceAdapter;
import projet.uf.modules.notification.application.port.NotificationService;
import projet.uf.modules.notification.application.port.out.NotifScheduledPersistencePort;
import projet.uf.modules.notification.application.port.out.NotifUserSettingsPersistencePort;
import projet.uf.modules.user.application.port.out.UserPersistencePort;

@Configuration
public class NotificationConfig {
    @Bean
    public NotifUserSettingsPersistencePort notifUserSettingsPersistencePort(JpaNotifUserSettingsRepository repository) {
        return new NotifUserSettingsPersistenceAdapter(repository);
    }
    @Bean
    public NotifScheduledPersistencePort notifScheduledPersistencePort(JpaNotifScheduledRepository repository) {
        return new NotifScheduledPersistenceAdapter(repository);
    }

    @Bean
    public NotificationService notificationService(
            NotifUserSettingsPersistencePort notifUserSettingsPersistencePort,
            NotifScheduledPersistencePort notifScheduledPersistencePort,
            UserPersistencePort userPersistencePort) {
        return new NotificationService(
                notifUserSettingsPersistencePort,
                notifScheduledPersistencePort,
                userPersistencePort
        );
    }
}
