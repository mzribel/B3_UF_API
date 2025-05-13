package projet.uf.modules.user.adapters.in.rest;

import org.springframework.web.bind.annotation.*;
import projet.uf.modules.user.application.ports.in.CreateUserUseCase;
import projet.uf.modules.user.application.ports.in.GetUserUseCase;
import projet.uf.modules.user.application.ports.in.CreateUserCommand;
import projet.uf.modules.user.domain.model.User;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    final GetUserUseCase getUserUseCase;

    public UserController(GetUserUseCase getUserUseCase) {
        this.getUserUseCase = getUserUseCase;
    }

    @GetMapping("/users/")
    public List<User> getUsers() throws Exception {
        return getUserUseCase.getAll();
    }

    @GetMapping("/users/{id}/")
    public Optional<User> getUserById(@PathVariable Long id) throws Exception {
        return getUserUseCase.getById(id);
    }
}
