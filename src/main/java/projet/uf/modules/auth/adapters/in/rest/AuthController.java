package projet.uf.modules.auth.adapters.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import projet.uf.exceptions.ApiError;
import projet.uf.modules.auth.adapters.in.rest.dto.AuthenticatedUserDto;
import projet.uf.modules.auth.application.ports.in.AuthUseCase;
import projet.uf.modules.auth.application.ports.in.LoginCommand;
import projet.uf.modules.auth.application.ports.in.RegisterCommand;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentification", description = "Inscription et connexion des utilisateurs")
public class AuthController {
    final AuthUseCase authUseCase;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticatedUserDto register(@Valid @RequestBody RegisterCommand command) {
        return authUseCase.register(command);
    }

    @PostMapping("/login")
    @Operation(summary = "Inscription d'un nouvel utilisateur et génération d'un token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Utilisateur connecté"),
            @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "409", description = "Un utilisateur avec cette adresse email existe déjà", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public AuthenticatedUserDto login(@Valid @RequestBody LoginCommand command) {
        return authUseCase.login(command);
    }
}
