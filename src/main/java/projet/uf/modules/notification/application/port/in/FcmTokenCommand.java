package projet.uf.modules.notification.application.port.in;

import jakarta.validation.constraints.NotBlank;

public record FcmTokenCommand(
        @NotBlank(message = "Le token ne peut pas être vide")
        String token
) {
}
