package projet.uf.modules.notification.adapter.out.persistence.notif_type;

import lombok.AllArgsConstructor;
import projet.uf.modules.notification.application.port.out.NotifTypePersistencePort;

@AllArgsConstructor
public class NotifTypePersistenceAdapter implements NotifTypePersistencePort {
    public final JpaNotifTypeRepository typeRepository;
}
