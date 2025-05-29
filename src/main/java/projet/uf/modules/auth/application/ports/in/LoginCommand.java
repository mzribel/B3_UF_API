package projet.uf.modules.auth.application.ports.in;

import jakarta.validation.constraints.NotBlank;

public record LoginCommand(
        @NotBlank String email,
        @NotBlank String password) {
}
