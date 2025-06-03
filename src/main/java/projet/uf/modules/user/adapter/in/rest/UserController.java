package projet.uf.modules.user.adapter.in.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import projet.uf.modules.auth.adapters.in.rest.security.CurrentUserProvider;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.user.application.dto.AUserDto;
import projet.uf.modules.user.application.port.in.UserUseCase;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Utilisateurs", description = "Gestion des utilisateurs de l'application")
public class UserController {
    final UserUseCase userUseCase;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping("/users")
    public List<AUserDto> getUsers() {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return userUseCase.getAll(operator);
    }

    @GetMapping("/users/{id}")
    public AUserDto getUserById(@PathVariable Long id) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return userUseCase.getById(id, operator);
    }

    @GetMapping("/users/admin")
    public List<AUserDto> getAdminUsers() {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return userUseCase.getAllAdminUsers(operator);
    }

    @PatchMapping("/users/{userId}/admin/promote")
    public void promoteUserToAdmin(@PathVariable Long userId) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        userUseCase.promoteUserToAdmin(userId, operator);
    }

    @PatchMapping("/users/{userId}/admin/demote")
    public void demoteUserFromAdmin(@PathVariable Long userId) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        userUseCase.demoteUserFromAdmin(userId, operator);
    }
}
