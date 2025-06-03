package projet.uf.modules.notification.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projet.uf.modules.notification.adapter.out.persistence.JpaUserNotificationPreferenceRepository;
import projet.uf.modules.notification.adapter.out.persistence.NotificationPersistenceAdapter;
import projet.uf.modules.notification.application.port.NotificationService;
import projet.uf.modules.notification.application.port.out.NotificationPersistencePort;
import projet.uf.modules.user.application.port.out.UserPersistencePort;

@Configuration
public class NotificationConfig {
    @Bean
    public NotificationPersistencePort notificationPersistencePort(JpaUserNotificationPreferenceRepository repository) {
        return new NotificationPersistenceAdapter(repository);
    }
    @Bean
    public NotificationService notificationService(NotificationPersistencePort notificationPersistencePort, UserPersistencePort userPersistencePort) {
        return new NotificationService(notificationPersistencePort, userPersistencePort);
    }
}
