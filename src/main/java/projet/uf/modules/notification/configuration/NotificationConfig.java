package projet.uf.modules.notification.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projet.uf.modules.notification.adapter.out.persistence.notif_scheduled.JpaNotifScheduledRepository;
import projet.uf.modules.notification.adapter.out.persistence.notif_scheduled.NotifScheduledPersistenceAdapter;
import projet.uf.modules.notification.adapter.out.persistence.notif_sent_log.JpaNotifSentLogRepository;
import projet.uf.modules.notification.adapter.out.persistence.notif_sent_log.NotifSentLogPersistenceAdapter;
import projet.uf.modules.notification.adapter.out.persistence.notif_type.JpaNotifTypeRepository;
import projet.uf.modules.notification.adapter.out.persistence.notif_type.NotifTypePersistenceAdapter;
import projet.uf.modules.notification.adapter.out.persistence.notif_user_settings.JpaNotifUserSettingsRepository;
import projet.uf.modules.notification.adapter.out.persistence.notif_user_settings.NotifUserSettingsPersistenceAdapter;
import projet.uf.modules.notification.application.port.NotificationService;
import projet.uf.modules.notification.application.port.out.*;
import projet.uf.modules.user.application.port.out.UserPersistencePort;

@Configuration
public class NotificationConfig {
    @Bean
    public NotifUserSettingsPersistencePort notifUserSettingsPersistencePort(JpaNotifUserSettingsRepository repository) {
        return new NotifUserSettingsPersistenceAdapter(repository);
    }
    @Bean
    public NotifSentLogPersistencePort sentLogPersistencePort(JpaNotifSentLogRepository repository) {
        return new NotifSentLogPersistenceAdapter(repository);
    }
    @Bean
    public NotifTypePersistencePort notifTypePersistencePort(JpaNotifTypeRepository repository) {
        return new NotifTypePersistenceAdapter(repository);
    }
    @Bean
    public NotifScheduledPersistencePort notifScheduledPersistencePort(JpaNotifScheduledRepository repository) {
        return new NotifScheduledPersistenceAdapter(repository);
    }

    @Bean
    public NotificationService notificationService(
            NotifUserSettingsPersistencePort tokens,
            NotifSentLogPersistencePort sentLogs,
            NotifTypePersistencePort types,
            NotifScheduledPersistencePort scheduled,
            UserPersistencePort userPersistencePort) {
        return new NotificationService(
                tokens,
                sentLogs,
                types,
                scheduled,
                userPersistencePort
        );
    }
}
