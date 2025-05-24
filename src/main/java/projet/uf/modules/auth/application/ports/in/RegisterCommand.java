package projet.uf.modules.auth.application.ports.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterCommand(
        @Email(message = "Format de l'email invalide")
        @NotBlank String email,
        @NotBlank String password,
        @Size(max = 20, message = "Le nom d'affichage ne peut pas dépasser 20 caractères")
        String displayName
) {}
