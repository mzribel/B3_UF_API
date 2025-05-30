package projet.uf.modules.breeder.application.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCatteryCommand(
        @NotBlank(message = "Le nom de la chatterie ne peut pas être vide")
        @NotNull(message = "Le nom de la chatterie ne peut pas être vide")
        @Size(max = 30, message = "Le nom de la chatterie ne peut pas dépasser 30 caractères")
        String name,
        Long userId
) {
}
