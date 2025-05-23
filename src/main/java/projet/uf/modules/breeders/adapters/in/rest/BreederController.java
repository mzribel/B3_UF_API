package projet.uf.modules.breeders.adapters.in.rest;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import projet.uf.modules.auth.application.model.CurrentUser;
import projet.uf.modules.breeders.application.ports.in.BreederService;
import projet.uf.modules.breeders.domain.model.Breeder;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class BreederController {
    final BreederService breederService;

    @GetMapping("/breeders/")
    public List<Breeder> getAll() {
        return breederService.getAll();
    }
    @GetMapping("/breeders/{id}/")
    public Optional<Breeder> getById(@PathVariable Long id) {
        return breederService.getById(id);
    }

    @GetMapping("/breeders/test/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String test(@AuthenticationPrincipal CurrentUser currentUser) {
        return currentUser.getId().toString();
    }
}
