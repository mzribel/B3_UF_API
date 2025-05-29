package projet.uf.modules.user.adapter.in.rest;

import org.springframework.web.bind.annotation.*;
import projet.uf.modules.user.application.port.in.UserUseCase;
import projet.uf.modules.user.domain.model.User;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }


    @GetMapping({"/users/", "/users"})
    public List<User> getUsers() {
        return userUseCase.getAll();
    }

    @GetMapping( {"/users/{id}/", "/users/{id}"})
    public Optional<User> getUserById(@PathVariable Long id) {
        return userUseCase.getById(id);
    }
}
