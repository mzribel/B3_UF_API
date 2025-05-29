package projet.uf.modules.breeder.adapter.in.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import projet.uf.modules.auth.adapters.in.rest.security.CurrentUserProvider;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.model.CatteryDetails;
import projet.uf.modules.breeder.application.model.CreateCatteryCommand;
import projet.uf.modules.breeder.application.port.in.CatteryUseCase;

import java.util.List;

@RestController
@AllArgsConstructor
public class CatteryController {
    final CatteryUseCase catteryUseCase;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping({"/catteries/", "/catteries"})
    public List<CatteryDetails> getAll() {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return catteryUseCase.getAll(operator);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping({"/catteries/", "/catteries"})
    public CatteryDetails create(
            @RequestParam(required = false, name = "userId") Long providedUserId,
            @RequestBody @Valid CreateCatteryCommand command
            ) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return catteryUseCase.create(providedUserId, command.name(), operator);
    }

    @GetMapping({"/catteries/{id}", "/catteries/{id}"})
    public CatteryDetails getById(@PathVariable Long id) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return catteryUseCase.getById(id, operator);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping({"/catteries/{id}/", "/catteries/{id}"})
    public void deleteById(@PathVariable Long id) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        catteryUseCase.deleteById(id, operator);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping({"/catteries/{id}/users/", "/catteries/{id}/users"})
    public void addUserToCattery(
            @PathVariable Long id,
            @RequestParam(required = false, name = "userEmail") String userEmail,
            @RequestParam(required = false, name = "userId") Long userId)
    {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        catteryUseCase.addUserToCattery(id, userId, userEmail, operator);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping({"/catteries/{catteryId}/users/{userId}/", "/catteries/{catteryId}/users/{userId}"})
    public void removeUserFromCattery(
            @PathVariable Long userId,
            @PathVariable Long catteryId)
    {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        catteryUseCase.removeUserFromCattery(catteryId, userId, operator);
    }


}
