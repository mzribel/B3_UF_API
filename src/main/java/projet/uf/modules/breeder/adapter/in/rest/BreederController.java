package projet.uf.modules.breeder.adapter.in.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import projet.uf.modules.auth.adapters.in.rest.security.CurrentUserProvider;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.command.CreateContactBreederCommand;
import projet.uf.modules.breeder.application.command.UpdateCatteryBreederCommand;
import projet.uf.modules.breeder.application.port.in.BreederUseCase;
import projet.uf.modules.breeder.domain.model.Breeder;
import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Eleveurs", description = "Gestion des informations des éleveurs physiques")
public class BreederController {
    final BreederUseCase breederUseCase;
    private final CurrentUserProvider currentUserProvider;

    // TOUS LES BREEDERS SANS DISTINCTION (admin only)
    @GetMapping("/breeders")
    public List<Breeder> getAll() {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return breederUseCase.getAll(operator);
    }

    @GetMapping("/breeders/{id}")
    public Breeder getById(@PathVariable Long id) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return breederUseCase.getById(id, operator);
    }

    @DeleteMapping("/breeders/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        breederUseCase.deleteById(id, operator);
    }

    // CATTERY BREEDER
    @GetMapping("/catteries/{catteryId}/breeder")
    public Breeder getCatteryBreeder(@PathVariable Long catteryId) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return breederUseCase.getCatteryBreederByCatteryId(catteryId, operator);
    }

    @PutMapping("/catteries/{catteryId}/breeder")
    public Breeder updateCatteryBreeder(
            @PathVariable Long catteryId,
            @RequestBody @Valid UpdateCatteryBreederCommand command) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return breederUseCase.updateCatteryBreeder(catteryId, command, operator);
    }

    // CONTACT BREEDERS
    @GetMapping("/catteries/{catteryId}/contacts/breeders/")
    public List<Breeder> getAllContactBreeders(@PathVariable Long catteryId) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return breederUseCase.getAllContactBreedersByCatteryId(catteryId, operator);
    }

    @PostMapping("/catteries/{catteryId}/contacts/breeders")
    @ResponseStatus(HttpStatus.CREATED)
    public Breeder createContactBreeder(
            @PathVariable Long catteryId,
            @RequestBody @Valid CreateContactBreederCommand command) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return breederUseCase.createContactBreeder(command, catteryId, operator);
    }

    @PutMapping("/catteries/{catteryId}/contacts/breeders/{breederId}")
    public Breeder updateContactBreeder(
            @PathVariable Long catteryId,
            @PathVariable Long breederId,
            @RequestBody @Valid CreateContactBreederCommand command) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return breederUseCase.updateContactBreeder(breederId, catteryId, command, operator);
    }

    @DeleteMapping("/catteries/{catteryId}/contacts/breeders/{breederId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContactBreeder(
            @PathVariable Long catteryId,
            @PathVariable Long breederId) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        breederUseCase.deleteContactBreeder(breederId, catteryId, operator);
    }

}
