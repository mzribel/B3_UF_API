package projet.uf.modules.auth.application.ports.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterCommand(
        @Email(message = "Format de l'email invalide")
        @NotBlank(message = "L'email ne peut pas être vide")
        String email,

        @NotBlank(message = "Le mot de passe ne peut pas être vide")
        String password,

        @Size(max = 20, message = "Le nom d'affichage ne peut pas dépasser 20 caractères")
        String displayName
) {}
