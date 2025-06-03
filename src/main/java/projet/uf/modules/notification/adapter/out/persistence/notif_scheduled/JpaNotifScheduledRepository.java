package projet.uf.modules.notification.adapter.out.persistence.notif_scheduled;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaNotifScheduledRepository extends JpaRepository<NotifScheduledEntity, Long> {
    List<NotifScheduledEntity> findBySentFalseAndScheduledAtBefore(LocalDateTime date);
    List<NotifScheduledEntity> findByUserIdAndScheduledAtAfterAndSent(Long userId, LocalDateTime date, Boolean sent);
}
