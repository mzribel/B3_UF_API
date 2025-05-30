package projet.uf.modules.loof_characteristic.adapter.in.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.loof_characteristic.application.port.in.CreateLoofCharacteristicCommand;
import projet.uf.modules.loof_characteristic.application.port.in.LoofCharacteristicUseCase;
import projet.uf.modules.loof_characteristic.domain.model.CoatPattern;
import projet.uf.modules.loof_characteristic.exception.LoofCharacteristicAlreadyExists;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/loof/characteristics/coat-patterns")
public class CoatPatternController {
    final LoofCharacteristicUseCase<CoatPattern> coatPatternUseCase;

    @GetMapping
    public List<CoatPattern> getAll() {
        return coatPatternUseCase.getAll();
    }

    @GetMapping("/{id}")
    public Optional<CoatPattern> getById(@PathVariable Long id) {
        return Optional.ofNullable(coatPatternUseCase.getById(id)
            .orElseThrow(() -> new ApiException("Aucune couleur de robe (coat color) trouvée avec l'id " + id, HttpStatus.NOT_FOUND)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public CoatPattern createCoatPattern(@Valid @RequestBody CreateLoofCharacteristicCommand command) throws LoofCharacteristicAlreadyExists {
        return coatPatternUseCase.create(command);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public CoatPattern updateCoatPattern(@PathVariable Long id, @Valid @RequestBody CreateLoofCharacteristicCommand command) throws LoofCharacteristicAlreadyExists {
        return coatPatternUseCase.update(id, command);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCoatPattern(@PathVariable Long id) {
        coatPatternUseCase.deleteById(id);
    }
}
