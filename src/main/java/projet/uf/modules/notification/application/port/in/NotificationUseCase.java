package projet.uf.modules.notification.application.port.in;

import projet.uf.modules.auth.application.model.OperatorUser;

public interface NotificationUseCase {
    void registerToken(String token, OperatorUser operatorUser);
    void sendNotificationToToken(String title, String body, OperatorUser operator);
}
