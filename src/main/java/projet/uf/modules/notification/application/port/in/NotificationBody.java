package projet.uf.modules.notification.application.port.in;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NotificationBody(
        @NotNull @Size(min = 1, max = 255) String title,
        @NotNull @Size(min = 1, max = 255) String message
) {

}
