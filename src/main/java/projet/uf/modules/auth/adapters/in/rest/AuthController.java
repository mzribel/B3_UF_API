package projet.uf.modules.auth.adapters.in.rest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import projet.uf.modules.auth.adapters.in.rest.dto.AuthenticatedUserDto;
import projet.uf.modules.auth.adapters.out.security.JwtService;
import projet.uf.modules.auth.application.ports.in.AuthUseCase;
import projet.uf.modules.auth.application.ports.in.LoginCommand;
import projet.uf.modules.auth.application.ports.in.RegisterCommand;
import projet.uf.modules.user.application.dto.UserDto;
import projet.uf.modules.user.domain.model.User;

@RestController
public class AuthController {
    final AuthUseCase authUseCase;
    final JwtService jwtService;

    public AuthController(AuthUseCase authUseCase, JwtService jwtService) {
        this.authUseCase = authUseCase;
        this.jwtService = jwtService;
    }

    @PostMapping({"/auth/register/", "/auth/register"})
    @ResponseStatus(HttpStatus.CREATED) // 👈 ici !
    public AuthenticatedUserDto register(@Valid @RequestBody RegisterCommand command) {
        User createdUser = authUseCase.register(command);

        return new AuthenticatedUserDto(
                UserDto.toDto(createdUser),
                jwtService.generateToken(createdUser)
        );
    }

    @PostMapping({"/auth/login/", "/auth/login"})
    public AuthenticatedUserDto login(@Valid @RequestBody LoginCommand command) {
        User authenticatedUser =  authUseCase.login(command);

        return new AuthenticatedUserDto(
            UserDto.toDto(authenticatedUser),
            jwtService.generateToken(authenticatedUser)
        );
    }
}
