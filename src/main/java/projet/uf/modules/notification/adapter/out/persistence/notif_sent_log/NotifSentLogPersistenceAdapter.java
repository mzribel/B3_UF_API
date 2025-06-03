package projet.uf.modules.notification.adapter.out.persistence.notif_sent_log;

import lombok.AllArgsConstructor;
import projet.uf.modules.notification.application.port.out.NotifSentLogPersistencePort;

@AllArgsConstructor
public class NotifSentLogPersistenceAdapter implements NotifSentLogPersistencePort {
    public final JpaNotifSentLogRepository typeRepository;
}
