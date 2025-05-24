package projet.uf.modules.auth.application.ports.in;

import jakarta.validation.constraints.NotBlank;

public record RegisterCommand(
        @NotBlank String email,
        @NotBlank String password,
        String displayName) {}
