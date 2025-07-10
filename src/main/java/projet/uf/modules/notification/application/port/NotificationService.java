package projet.uf.modules.notification.application.port;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.notification.application.port.in.FcmTokenCommand;
import projet.uf.modules.notification.application.port.in.NotificationCommand;
import projet.uf.modules.notification.application.port.in.NotificationUseCase;
import projet.uf.modules.notification.application.port.in.ScheduledNotificationCommand;
import projet.uf.modules.notification.application.port.out.*;
import projet.uf.modules.notification.domain.NotifScheduled;
import projet.uf.modules.notification.domain.NotifUserSettings;
import projet.uf.modules.user.application.port.out.UserPersistencePort;
import projet.uf.modules.user.domain.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public class NotificationService implements NotificationUseCase {
    public final NotifUserSettingsPersistencePort settingsPersistencePort;
    public final NotifScheduledPersistencePort scheduledPersistencePort;
    public final UserPersistencePort userPersistencePort;

//    public final NotificationPreferencesPersistencePort notificationpersistencePort;
//    public final UserPersistencePort userPersistencePort;
//
//    public void sendNotificationToUser(String title, String body, OperatorUser operator) {
//        Optional<UserNotificationPreference> model = notificationpersistencePort.getUserNotificationPreferenceByUserId(operator.getId());
//        if (model.isEmpty() || model.get().getFcmToken() == null) {
//            throw new ApiException("Utilisateur n'a pas enregistré de Token", HttpStatus.BAD_REQUEST);
//        }
//
//        Message message = Message.builder()
//                .setToken(model.get().getFcmToken())
//                .setNotification(Notification.builder()
//                        .setTitle(title)
//                        .setBody(body)
//                        .build())
//                .build();
//
//        try {
//            String response = FirebaseMessaging.getInstance().send(message);
//            System.out.println("✅ Notification envoyée : " + response);
//        } catch (FirebaseMessagingException e) {
//            System.err.println("❌ Échec de l'envoi de la notification : " + e.getMessage());
//        }
//    }
//
//    public void registerFcmToken(String token, OperatorUser operatorUser) {
//        if (!userPersistencePort.existsById(operatorUser.getId())) {
//            throw new ApiException("wtf bro", HttpStatus.BAD_REQUEST);
//        }
//
//        Optional<UserNotificationPreference> optModel = notificationpersistencePort.getUserNotificationPreferenceByUserId(operatorUser.getId());
//        System.out.println(optModel);
//        UserNotificationPreference model = optModel.orElseGet(() -> new UserNotificationPreference(
//                operatorUser.getId(),
//                true,
//                token
//        ));
//        System.out.println(model.getId());
//        model.setFcmToken(token);
//        notificationpersistencePort.save(model);
//    }

    public void registerFcmToken(FcmTokenCommand command, OperatorUser operatorUser) {
        User user = userPersistencePort.getById(operatorUser.getId())
                .orElseThrow(() -> new ApiException("Utilisateur introuvable", HttpStatus.BAD_REQUEST));

        NotifUserSettings settings = settingsPersistencePort.getSettingsByUserId(user.getId())
                .orElseGet(() -> new NotifUserSettings(operatorUser.getId()));

        settings.setFcmToken(command.token());
        settingsPersistencePort.save(settings);
    }

    public void sendNotificationToUser(NotificationCommand command, Long userId, OperatorUser operator) {
        if (!operator.isAdmin()) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }
        User user = userPersistencePort.getById(userId)
                .orElseThrow(() -> new ApiException("Utilisateur introuvable", HttpStatus.BAD_REQUEST));

        Optional<NotifUserSettings> settings = settingsPersistencePort.getSettingsByUserId(userId);
        if(settings.isEmpty() || settings.get().getFcmToken() == null) {
            throw new ApiException("L'utilisateur n'a pas renseigné de token FCM valide", HttpStatus.BAD_REQUEST);
        }
        if(!settings.get().isEnabled()) {
            throw new ApiException("L'utilisateur n'accepte pas les notifications", HttpStatus.BAD_REQUEST);
        }

        sendNotification(settings.get().getFcmToken(), command.title(), command.description());
    }

    public NotifScheduled registerScheduledNotification(ScheduledNotificationCommand command, Long userId, OperatorUser operator) {
        if (!operator.isAdmin()) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }
        if (!userPersistencePort.existsById(userId)) {
            throw new ApiException("Utilisateur introuvable", HttpStatus.BAD_REQUEST);
        }

        // TODO : Proper timezone management
        if (command.scheduledAt().isBefore(LocalDateTime.now())) {
            throw new ApiException("La date doit être dans le futur", HttpStatus.BAD_REQUEST);
        }

        NotifScheduled scheduled = command.toModel(userId);
        return scheduledPersistencePort.save(scheduled);
    }

    public List<NotifScheduled> getScheduledNotificationsForUser(Long userId, OperatorUser operator) {
        if (!operator.isAdmin() && !Objects.equals(userId, operator.getId())) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }
        return scheduledPersistencePort.getScheduledNotificationsByUserId(userId);
    }

    @Scheduled(fixedRate = 100000)
    private void sendScheduledNotifications() {
        List<NotifScheduled> scheduledNotifications = scheduledPersistencePort.getDueScheduledNotifications();
        for (NotifScheduled notif : scheduledNotifications) {
            try {
                sendScheduledNotification(notif);
            } catch (Exception e) {
                System.out.println("Impossible d'envoyer la notification");
            }
        }
    }

    private void sendScheduledNotification(NotifScheduled notifScheduled) {
        if (notifScheduled.getUserId() == null) {
            System.out.println("wtf");
            scheduledPersistencePort.markSent(notifScheduled.getId());
            return;
        }

        Optional<NotifUserSettings> settings = settingsPersistencePort.getSettingsByUserId(notifScheduled.getUserId());
        if(settings.isEmpty() || settings.get().getFcmToken() == null) {
            throw new ApiException("L'utilisateur n'a pas renseigné de token FCM valide", HttpStatus.BAD_REQUEST);
        }
        if(!settings.get().isEnabled()) {
            throw new ApiException("L'utilisateur n'accepte pas les notifications", HttpStatus.BAD_REQUEST);
        }
        sendNotification(settings.get().getFcmToken(), notifScheduled.getTitle(), notifScheduled.getDescription());
        scheduledPersistencePort.markSent(notifScheduled.getId());
    }

    private void sendNotification(String token, String title, String description) {
        System.out.println("Sending notification !!");
        Message message = Message.builder()
                .setToken(token)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(description)
                        .build())
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            throw new ApiException("Impossible d'envoyer le message : "+e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void registerToken(String token, OperatorUser operatorUser) {

    }

    @Override
    public void sendNotificationToToken(String title, String body, OperatorUser operator) {

    }
}
