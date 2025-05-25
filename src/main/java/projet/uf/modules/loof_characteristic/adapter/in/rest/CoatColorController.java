package projet.uf.modules.loof_characteristic.adapter.in.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.loof_characteristic.application.port.in.CreateLoofCharacteristicCommand;
import projet.uf.modules.loof_characteristic.application.port.in.LoofCharacteristicUseCase;
import projet.uf.modules.loof_characteristic.domain.model.CoatColor;
import projet.uf.modules.loof_characteristic.exception.LoofCharacteristicAlreadyExists;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping({"/loof/characteristics/coat-colors/", "/loof/characteristics/coat-colors"})
public class CoatColorController {
    final LoofCharacteristicUseCase<CoatColor> coatColorUseCase;

    @GetMapping
    public List<CoatColor> getAll() {
        return coatColorUseCase.getAll();
    }

    @GetMapping({"/{id}/", "/{id}"})
    public Optional<CoatColor> getById(@PathVariable Long id) {
        return Optional.ofNullable(coatColorUseCase.getById(id)
            .orElseThrow(() -> new ApiException("Aucune couleur de robe (coat color) trouvée avec l'id " + id, HttpStatus.NOT_FOUND)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public CoatColor createCoatColor(@Valid @RequestBody CreateLoofCharacteristicCommand command) throws LoofCharacteristicAlreadyExists {
        return coatColorUseCase.create(command);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping({"/{id}/", "/{id}"})
    public CoatColor updateCoatColor(@PathVariable Long id, @Valid @RequestBody CreateLoofCharacteristicCommand command) throws LoofCharacteristicAlreadyExists {
        return coatColorUseCase.update(id, command);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping({"/{id}/", "/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCoatColor(@PathVariable Long id) {
        coatColorUseCase.deleteById(id);
    }
}
