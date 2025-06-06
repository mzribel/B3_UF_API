package projet.uf.modules.notification.adapter.out.persistence.notif_sent_log;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNotifSentLogRepository extends JpaRepository<NotifSentLogEntity, Long> {
}
