package projet.uf.modules.auth.adapters.in.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import projet.uf.modules.auth.application.ports.in.AuthService;
import projet.uf.modules.auth.application.ports.in.LoginCommand;
import projet.uf.modules.auth.application.ports.in.RegisterCommand;
import projet.uf.modules.user.domain.model.User;

@RestController
public class AuthController {
    final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/register")
    public User register(@RequestBody RegisterCommand command) throws Exception {
        return authService.register(command);
    }

    @PostMapping("/auth/login")
    public String login(@RequestBody LoginCommand command) throws Exception {
        return authService.login(command);
    }
}
