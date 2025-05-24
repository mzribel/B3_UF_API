package projet.uf.modules.breeder.adapter.in.rest;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import projet.uf.modules.auth.domain.model.CurrentUser;
import projet.uf.modules.breeder.application.port.in.BreederUseCase;
import projet.uf.modules.breeder.domain.model.Breeder;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class BreederController {
    final BreederUseCase breederService;

    @GetMapping({"/breeders/", "/breeders"})
    public List<Breeder> getAll() {
        return breederService.getAll();
    }
    @GetMapping({"/breeders/{id}/", "/breeders/{id}"})
    public Optional<Breeder> getById(@PathVariable Long id) {
        return breederService.getById(id);
    }

    // TODO : route de tests pour les permissions ADMIN
    @GetMapping({"/breeders/test/", "/breeders/test"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public String test(@AuthenticationPrincipal CurrentUser currentUser) {
        return currentUser.id().toString();
    }
}
