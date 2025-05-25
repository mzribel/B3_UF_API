package projet.uf.modules.breeder.adapter.in.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.adapters.in.rest.security.CurrentUserProvider;
import projet.uf.modules.auth.domain.model.CurrentUser;
import projet.uf.modules.breeder.application.model.CatteryDetails;
import projet.uf.modules.breeder.application.port.in.CatteryUseCase;
import projet.uf.modules.breeder.domain.model.Cattery;

import java.util.List;

@RestController
@AllArgsConstructor
public class CatteryController {
    final CatteryUseCase catteryUseCase;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping({"/catteries/", "/catteries"})
    public List<CatteryDetails> getAll() {
        CurrentUser currentUser = currentUserProvider.getCurrentUser();
        // Admin : get all sans distinction
        if (currentUser.isAdmin()) {
            return catteryUseCase.getAll();
        }
        return catteryUseCase.getAllAccessibleFromUser(currentUser.id());
    }

    @GetMapping({"/catteries/{id}", "/catteries/{id}"})
    public CatteryDetails getById(@PathVariable Long id) {
        CurrentUser user = currentUserProvider.getCurrentUser();
        return user.isAdmin()
                ? catteryUseCase.getById(id)
                : catteryUseCase.getByIdIfAuthorized(id, user.id());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping({"/catteries/", "/catteries"})
    public Cattery create(@RequestParam(required = false, name = "userId") Long providedUserId) {
        CurrentUser currentUser = currentUserProvider.getCurrentUser();

        if (providedUserId != null) {
            if (!currentUser.isAdmin()) {
                throw new ApiException("Non autorisé", HttpStatus.UNAUTHORIZED);
            }
            return catteryUseCase.create(providedUserId);
        }

        return catteryUseCase.create(currentUser.id());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping({"/catteries/{id}/", "/catteries/{id}"})
    public void deleteById(@PathVariable Long id) {
        if (currentUserProvider.getCurrentUser().isAdmin()) {
            catteryUseCase.deleteById(id);
        } else {
            catteryUseCase.deleteByIdIfAuthorized(id, currentUserProvider.getCurrentUser().id());
        }
    }
}
