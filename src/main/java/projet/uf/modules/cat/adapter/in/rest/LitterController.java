package projet.uf.modules.cat.adapter.in.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import projet.uf.modules.auth.adapters.in.rest.security.CurrentUserProvider;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.cat.application.command.LitterCommand;
import projet.uf.modules.cat.application.ports.in.LitterUseCase;
import projet.uf.modules.cat.domain.model.Litter;

import java.util.List;

@RestController
@AllArgsConstructor
public class LitterController {
    private final LitterUseCase litterUseCase;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping("/litters")
    public List<Litter> getLitters() {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return litterUseCase.getAll(operator);
    }

    @GetMapping("/litters/{litterId}")
    public Litter getLitter(@PathVariable Long litterId) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return litterUseCase.getById(litterId, operator);
    }

    @PutMapping("/litters/{litterId}")
    public Litter updateLitter(
            @PathVariable Long litterId,
            @RequestBody LitterCommand command) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return litterUseCase.updateLitterById(litterId, command, operator);
    }

    @DeleteMapping("/litters/{litterId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLitter(@PathVariable Long litterId) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        litterUseCase.deleteLitterById(litterId, operator);
    }

    @GetMapping("/cats/{catId}/litter")
    public Litter getCatLitter(@PathVariable Long catId) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return litterUseCase.getCatLitter(catId, operator);
    }

    @GetMapping("/catteries/{catteryId}/litters")
    public List<Litter> getCatteryLitter(@PathVariable Long catteryId) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return litterUseCase.getByCatteryId(catteryId, operator);
    }

    @PostMapping("/catteries/{catteryId}/litters")
    @ResponseStatus(HttpStatus.CREATED)
    public Litter createLitter(
            @PathVariable Long catteryId,
            @RequestBody LitterCommand command) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return litterUseCase.createLitter(command, catteryId, operator);
    }
}
