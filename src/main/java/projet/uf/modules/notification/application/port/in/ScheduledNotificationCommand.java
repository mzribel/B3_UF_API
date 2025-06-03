package projet.uf.modules.notification.application.port.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import projet.uf.modules.notification.domain.NotifScheduled;

import java.time.LocalDateTime;

public record ScheduledNotificationCommand(
        @NotBlank(message = "Le titre de la notification ne peut pas être vide")
        @Size(min = 1, max = 65, message = "Le titre de la notification doit faire entre 1 et 65 caractères")
        String title,
        @NotBlank(message = "La description de la notification ne peut pas être vide")
        @Size(min = 1, max = 240, message = "La description de la notification doit faire entre 1 et 240 caractères")
        String description,
        @NotNull
        LocalDateTime scheduledAt
) {
        public NotifScheduled toModel(Long userId) {
                return new NotifScheduled(
                        userId,
                        null,
                        this.title,
                        this.description,
                        this.scheduledAt
                );
        }
}
