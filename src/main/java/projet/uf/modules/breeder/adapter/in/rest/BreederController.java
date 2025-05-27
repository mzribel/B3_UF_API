package projet.uf.modules.breeder.adapter.in.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import projet.uf.modules.auth.adapters.in.rest.security.CurrentUserProvider;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.model.CreateContactBreederCommand;
import projet.uf.modules.breeder.application.model.UpdateCatteryBreederCommand;
import projet.uf.modules.breeder.application.port.in.BreederUseCase;
import projet.uf.modules.breeder.domain.model.Breeder;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class BreederController {
    final BreederUseCase breederUseCase;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping({"/breeders/", "/breeders"})
    public List<Breeder> getAll() {
        return breederUseCase.getAll();
    }

    @GetMapping({"/breeders/{id}/", "/breeders/{id}"})
    public Optional<Breeder> getById(@PathVariable Long id) {
        return breederUseCase.getById(id);
    }

    @PostMapping({"/catteries/{catteryId}/contacts/breeders/", "/catteries/{catteryId}/contacts/breeders"})
    @ResponseStatus(HttpStatus.CREATED)
    public Breeder createContactBreeder(
            @PathVariable Long catteryId,
            @RequestBody @Valid CreateContactBreederCommand command) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return breederUseCase.createContact(command, catteryId, operator);
    }

    @PutMapping({"/catteries/{catteryId}/breeder/", "/catteries/{catteryId}/breeder"})
    public Breeder updateCatteryBreeder(
            @PathVariable Long catteryId,
            @RequestBody @Valid UpdateCatteryBreederCommand command) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return breederUseCase.updateCatteryBreeder(catteryId, command, operator);
    }

    @PutMapping({"/catteries/{catteryId}/contacts/breeders/{breederId}/", "/catteries/{catteryId}/contacts/breeders/{breederId}"})
    public Breeder updateCatteryBreeder(
            @PathVariable Long catteryId,
            @PathVariable Long breederId,
            @RequestBody @Valid CreateContactBreederCommand command) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return breederUseCase.updateContactBreeder(breederId, catteryId, command, operator);
    }
}
