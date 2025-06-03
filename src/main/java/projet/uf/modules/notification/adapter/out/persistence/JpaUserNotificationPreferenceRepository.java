package projet.uf.modules.notification.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserNotificationPreferenceRepository extends JpaRepository<UserNotificationPreferenceEntity, Long> {
    Optional<UserNotificationPreferenceEntity> findByUserId(Long userId);
}
