package projet.uf.modules.user.adapter.in.rest;

import org.springframework.web.bind.annotation.*;
import projet.uf.modules.user.application.port.in.UserUseCase;
import projet.uf.modules.user.domain.model.User;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    final UserUseCase userService;

    public UserController(UserUseCase userService) {
        this.userService = userService;
    }


    @GetMapping({"/users/", "/users"})
    public List<User> getUsers() throws Exception {
        return userService.getAll();
    }

    @GetMapping( {"/users/{id}/", "/users/{id}"})
    public Optional<User> getUserById(@PathVariable Long id) throws Exception {
        return userService.getById(id);
    }
}
