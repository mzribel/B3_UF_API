package projet.uf.modules.notification.adapter.out.persistence.notif_user_settings;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaNotifUserSettingsRepository extends JpaRepository<NotifUserSettingsEntity, Long> {
    Optional<NotifUserSettingsEntity> findByUserId(Long userId);
}
