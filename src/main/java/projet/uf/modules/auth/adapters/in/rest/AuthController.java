package projet.uf.modules.auth.adapters.in.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import projet.uf.modules.auth.application.ports.in.RegisterUseCase;
import projet.uf.modules.user.application.ports.in.CreateUserCommand;
import projet.uf.modules.user.domain.model.User;

@RestController
public class AuthController {
    final RegisterUseCase registerUseCase;

    public AuthController(RegisterUseCase registerUseCase) {
        this.registerUseCase = registerUseCase;
    }

    @PostMapping("/auth/register")
    public User register(@RequestBody CreateUserCommand command) throws Exception {
        return registerUseCase.register(command);
    }
}
