package projet.uf.modules.notification.application.port;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.notification.application.port.in.NotificationUseCase;
import projet.uf.modules.notification.application.port.out.NotificationPersistencePort;
import projet.uf.modules.notification.domain.UserNotificationPreference;
import projet.uf.modules.user.application.port.out.UserPersistencePort;

import java.util.Optional;

@AllArgsConstructor
public class NotificationService implements NotificationUseCase {
    public final NotificationPersistencePort notificationpersistencePort;
    public final UserPersistencePort userPersistencePort;

    public void sendNotificationToToken(String title, String body, OperatorUser operator) {
        Optional<UserNotificationPreference> model = notificationpersistencePort.getUserNotificationPreferenceByUserId(operator.getId());
        if (model.isEmpty() || model.get().getFcmToken() == null) {
            throw new ApiException("Utilisateur n'a pas enregistré de Token", HttpStatus.BAD_REQUEST);
        }

        Message message = Message.builder()
                .setToken(model.get().getFcmToken())
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("✅ Notification envoyée : " + response);
        } catch (FirebaseMessagingException e) {
            System.err.println("❌ Échec de l'envoi de la notification : " + e.getMessage());
        }
    }

    public void registerToken(String token, OperatorUser operatorUser) {
        if (!userPersistencePort.existsById(operatorUser.getId())) {
            throw new ApiException("wtf bro", HttpStatus.BAD_REQUEST);
        }

        Optional<UserNotificationPreference> optModel = notificationpersistencePort.getUserNotificationPreferenceByUserId(operatorUser.getId());
        System.out.println(optModel);
        UserNotificationPreference model = optModel.orElseGet(() -> new UserNotificationPreference(
                operatorUser.getId(),
                true,
                token
        ));
        System.out.println(model.getId());
        model.setFcmToken(token);
        notificationpersistencePort.save(model);
    }
}
