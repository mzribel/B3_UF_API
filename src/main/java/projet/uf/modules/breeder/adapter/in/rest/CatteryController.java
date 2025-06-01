package projet.uf.modules.breeder.adapter.in.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import projet.uf.modules.auth.adapters.in.rest.security.CurrentUserProvider;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.command.AddUserToCatteryCommand;
import projet.uf.modules.breeder.application.dto.CatteryDetailsDto;
import projet.uf.modules.breeder.application.command.CreateCatteryCommand;
import projet.uf.modules.breeder.application.dto.UserCatteriesDto;
import projet.uf.modules.breeder.application.port.in.CatteryUseCase;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Eleveurs", description = "Gestion des informations d'une chatterie applicative")
public class CatteryController {
    final CatteryUseCase catteryUseCase;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping("/catteries")
    public List<CatteryDetailsDto> getAll() {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return catteryUseCase.getAll(operator);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/catteries")
    public CatteryDetailsDto create(
            @RequestBody @Valid CreateCatteryCommand command
            ) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return catteryUseCase.create(command, operator);
    }

    @GetMapping("/catteries/{id}")
    public CatteryDetailsDto getById(@PathVariable Long id) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return catteryUseCase.getById(id, operator);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/catteries/{id}")
    public void deleteById(@PathVariable Long id) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        catteryUseCase.deleteById(id, operator);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping( "/catteries/{id}/members")
    public void addUserToCattery(
            @PathVariable Long id,
            @RequestBody AddUserToCatteryCommand command
            )
    {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        catteryUseCase.addUserToCattery(id, command, operator);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/catteries/{catteryId}/members/{userId}")
    public void removeUserFromCattery(
            @PathVariable Long userId,
            @PathVariable Long catteryId)
    {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        catteryUseCase.removeUserFromCattery(catteryId, userId, operator);
    }

    @GetMapping("/users/{userId}/catteries")
    public UserCatteriesDto getAllUserCatteries(@PathVariable Long userId) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return catteryUseCase.getUserCatteries(userId, operator);
    }
}
