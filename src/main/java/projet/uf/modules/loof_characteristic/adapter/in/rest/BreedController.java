package projet.uf.modules.loof_characteristic.adapter.in.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.loof_characteristic.application.port.in.CreateLoofCharacteristicCommand;
import projet.uf.modules.loof_characteristic.application.port.in.LoofCharacteristicUseCase;
import projet.uf.modules.loof_characteristic.domain.model.Breed;
import projet.uf.modules.loof_characteristic.exception.LoofCharacteristicAlreadyExists;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/loof/characteristics/breeds")
@Tag(name = "Caractéristiques LOOF", description = "Gestion des caractéristiques légales du LOOF")
public class BreedController {
    final LoofCharacteristicUseCase<Breed> breedUseCase;

    @GetMapping
    public List<Breed> getAll() {
        return breedUseCase.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Breed> getById(@PathVariable Long id) {
        return Optional.ofNullable(breedUseCase.getById(id)
            .orElseThrow(() -> new ApiException("Aucune race (breed) trouvée avec l'id " + id, HttpStatus.NOT_FOUND)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public Breed createBreed(@Valid @RequestBody CreateLoofCharacteristicCommand command) throws LoofCharacteristicAlreadyExists {
        return breedUseCase.create(command);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public Breed updateBreed(@PathVariable Long id, @Valid @RequestBody CreateLoofCharacteristicCommand command) throws LoofCharacteristicAlreadyExists {
        return breedUseCase.update(id, command);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBreed(@PathVariable Long id) {
        breedUseCase.deleteById(id);
    }
}
