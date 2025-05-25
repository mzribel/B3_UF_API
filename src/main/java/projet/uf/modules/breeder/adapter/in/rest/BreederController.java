package projet.uf.modules.breeder.adapter.in.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import projet.uf.modules.breeder.application.port.in.BreederUseCase;
import projet.uf.modules.breeder.domain.model.Breeder;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class BreederController {
    final BreederUseCase breederUseCase;

    @GetMapping({"/breeders/", "/breeders"})
    public List<Breeder> getAll() {
        return breederUseCase.getAll();
    }

    @GetMapping({"/breeders/{id}/", "/breeders/{id}"})
    public Optional<Breeder> getById(@PathVariable Long id) {
        return breederUseCase.getById(id);
    }

}
