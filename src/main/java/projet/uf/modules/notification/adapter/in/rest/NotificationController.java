package projet.uf.modules.notification.adapter.in.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import projet.uf.modules.auth.adapters.in.rest.security.CurrentUserProvider;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.notification.application.port.NotificationService;
import projet.uf.modules.notification.application.port.in.FcmTokenCommand;
import projet.uf.modules.notification.application.port.in.NotificationBody;
import projet.uf.modules.notification.application.port.in.NotificationCommand;

@RestController
@AllArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final CurrentUserProvider currentUserProvider;

    @PostMapping("/users/{userId}/notifications/test")
    public void notifications(
            @PathVariable Long userId,
            @Valid @RequestBody NotificationCommand notification) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        notificationService.sendNotificationToUser(notification, userId, operator);
    }

    @PostMapping("/users/me/notifications/token")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveFcmToken(@RequestBody @Valid @NotNull FcmTokenCommand token) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        notificationService.registerFcmToken(token, operator);
    }
}
