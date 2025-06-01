package projet.uf.modules.user.adapter.in.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import projet.uf.modules.auth.adapters.in.rest.security.CurrentUserProvider;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.user.application.dto.UserDto;
import projet.uf.modules.user.application.port.in.UserUseCase;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Utilisateurs", description = "Gestion des utilisateurs de l'application")
public class UserController {
    final UserUseCase userUseCase;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping("/users")
    public List<UserDto> getUsers() {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return userUseCase.getAll(operator);
    }

    @GetMapping("/users/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userUseCase.getById(id);
    }
}
