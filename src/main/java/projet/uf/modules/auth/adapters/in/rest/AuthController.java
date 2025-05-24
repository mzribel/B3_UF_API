package projet.uf.modules.auth.adapters.in.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import projet.uf.modules.auth.adapters.in.rest.dto.AuthenticatedUserDto;
import projet.uf.modules.auth.adapters.out.security.JwtServiceImpl;
import projet.uf.modules.auth.application.ports.in.AuthService;
import projet.uf.modules.auth.application.ports.in.LoginCommand;
import projet.uf.modules.auth.application.ports.in.RegisterCommand;
import projet.uf.modules.user.adapter.in.rest.dto.UserDtoMapper;
import projet.uf.modules.user.domain.model.User;

@RestController
public class AuthController {
    final AuthService authService;
    final JwtServiceImpl jwtService;

    public AuthController(AuthService authService, JwtServiceImpl jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping({"/auth/register/", "/auth/register"})
    public AuthenticatedUserDto register(@RequestBody RegisterCommand command) throws Exception {
        User createdUser = authService.register(command);

        return new AuthenticatedUserDto(
                UserDtoMapper.toDto(createdUser),
                jwtService.generateToken(createdUser)
        );
    }

    @PostMapping({"/auth/login/", "/auth/login"})
    public AuthenticatedUserDto login(@RequestBody LoginCommand command) throws Exception {
        User authenticatedUser =  authService.login(command);

        return new AuthenticatedUserDto(
            UserDtoMapper.toDto(authenticatedUser),
            jwtService.generateToken(authenticatedUser)
        );
    }
}
