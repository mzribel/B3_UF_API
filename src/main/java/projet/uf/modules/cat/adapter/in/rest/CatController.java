package projet.uf.modules.cat.adapter.in.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import projet.uf.modules.auth.adapters.in.rest.security.CurrentUserProvider;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.cat.application.dto.CatDetailsDto;
import projet.uf.modules.cat.application.ports.in.CatUseCase;
import projet.uf.modules.cat.application.command.CatCommand;
import projet.uf.modules.cat.domain.model.Cat;

import java.util.List;

@RestController
@AllArgsConstructor
public class CatController {
    final CatUseCase catUseCase;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping("/cats")
    public List<Cat> getAllCats() {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return catUseCase.getAll(operator);
    }

    @GetMapping("/cats/{catId}")
    public CatDetailsDto getCat(@PathVariable Long catId) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return catUseCase.getById(catId, operator);
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
    public List<Cat> getCatteryCats(@PathVariable Long id) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return catUseCase.getByCatteryId(id, operator);
    }

    @PutMapping("/cats/{id}")
    public Cat updateCat(@PathVariable Long id, @RequestBody CatCommand command) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        return catUseCase.updateCatById(id, command, operator);
    }

    @DeleteMapping( "/cats/{id}")
    public void deleteCat(@PathVariable Long id) {
        OperatorUser operator = OperatorUser.fromCurrentUser(currentUserProvider.getCurrentUser());
        catUseCase.deleteCatById(id, operator);
    }
}
