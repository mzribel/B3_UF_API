package projet.uf.users.adapters.in.rest;

import org.springframework.web.bind.annotation.*;
import projet.uf.users.application.ports.in.CreateUserUseCase;
import projet.uf.users.application.ports.in.GetUserUseCase;
import projet.uf.users.application.ports.in.CreateUserCommand;
import projet.uf.users.domain.model.User;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    final CreateUserUseCase createUserUseCase;
    final GetUserUseCase getUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase, GetUserUseCase getUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.getUserUseCase = getUserUseCase;
    }

    @PostMapping("/users/")
    public User create(@RequestBody CreateUserCommand command) throws Exception {
        return createUserUseCase.createUser(command);
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
