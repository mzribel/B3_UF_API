package projet.uf.modules.notification.adapter.in.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import projet.uf.modules.auth.adapters.in.rest.security.CurrentUserProvider;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.notification.application.port.NotificationService;
import projet.uf.modules.notification.application.port.in.NotificationBody;

@RestController
@AllArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final CurrentUserProvider currentUserProvider;

    @PostMapping("/users/me/notifications/test")
    public void notifications(@Valid @RequestBody NotificationBody notification) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        notificationService.sendNotificationToToken(notification.title(), notification.message(), operator);
    }

    @PostMapping("/users/me/notifications/token")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveFcmToken(@RequestBody @Valid @NotNull String token) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        notificationService.registerToken(token, operator);
    }
}
