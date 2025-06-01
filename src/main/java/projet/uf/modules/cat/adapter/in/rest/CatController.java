package projet.uf.modules.cat.adapter.in.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import projet.uf.modules.auth.adapters.in.rest.security.CurrentUserProvider;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.cat.application.dto.CatDetailsDto;
import projet.uf.modules.cat.application.dto.CatPedigreeDto;
import projet.uf.modules.cat.application.ports.in.CatUseCase;
import projet.uf.modules.cat.application.command.CatCommand;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Chats", description = "Gestion des chats d'une chatterie")
public class CatController {
    final CatUseCase catUseCase;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping("/cats")
    public List<CatDetailsDto> getAllCats() {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return catUseCase.getAll(operator);
    }

    @GetMapping("/cats/{catId}")
    public CatDetailsDto getCat(@PathVariable Long catId) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return catUseCase.getById(catId, operator);
    }

    @GetMapping("/cats/{catId}/pedigree")
    public CatPedigreeDto getCatPedigree(@PathVariable Long catId) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return catUseCase.getPedigreeById(catId, operator);
    }

    @PostMapping( "/catteries/{catteryId}/cats")
    @ResponseStatus(HttpStatus.CREATED)
    public CatDetailsDto createCat(
            @PathVariable Long catteryId,
            @RequestBody CatCommand command
    ) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return catUseCase.createCat(command, catteryId, operator);
    }

    @GetMapping( "/catteries/{id}/cats")
    public List<CatDetailsDto> getCatteryCats(@PathVariable Long id) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return catUseCase.getByCatteryId(id, operator);
    }

    @PutMapping("/cats/{id}")
    public CatDetailsDto updateCat(@PathVariable Long id, @RequestBody CatCommand command) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return catUseCase.updateCatById(id, command, operator);
    }

    @DeleteMapping( "/cats/{id}")
    public void deleteCat(@PathVariable Long id) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        catUseCase.deleteCatById(id, operator);
    }

    @GetMapping("/litters/{litterId}/cats")
    public List<CatDetailsDto> getLitterCats(
            @PathVariable Long litterId) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return catUseCase.getCatsByLitterId(litterId, operator);
    }

    // TODO : à changer en PUT /cats/id
    @PutMapping("/litters/{litterId}/cats/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addCatToLitter(
            @PathVariable Long catId,
            @PathVariable Long litterId) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        catUseCase.addKittenToLitter(catId, litterId, operator);
    }

    // TODO : à changer en PUT /cats/id
    @DeleteMapping("/litters/{litterId}/cats/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCatFromLitter(
            @PathVariable Long catId,
            @PathVariable Long litterId) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        catUseCase.removeKittenFromLitter(catId, litterId, operator);
    }
}
