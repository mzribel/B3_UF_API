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
import projet.uf.modules.loof_characteristic.domain.model.CoatEffect;
import projet.uf.modules.loof_characteristic.exception.LoofCharacteristicAlreadyExists;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/loof/characteristics/coat-effects")
@Tag(name = "Caractéristiques LOOF", description = "Gestion des caractéristiques légales du LOOF")
public class CoatEffectController {
    final LoofCharacteristicUseCase<CoatEffect> coatEffectUseCase;

    @GetMapping
    public List<CoatEffect> getAll() {
        return coatEffectUseCase.getAll();
    }

    @GetMapping("/{id}")
    public Optional<CoatEffect> getById(@PathVariable Long id) {
        return Optional.ofNullable(coatEffectUseCase.getById(id)
            .orElseThrow(() -> new ApiException("Aucune couleur de robe (coat color) trouvée avec l'id " + id, HttpStatus.NOT_FOUND)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public CoatEffect createCoatEffect(@Valid @RequestBody CreateLoofCharacteristicCommand command) throws LoofCharacteristicAlreadyExists {
        return coatEffectUseCase.create(command);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public CoatEffect updateCoatEffect(@PathVariable Long id, @Valid @RequestBody CreateLoofCharacteristicCommand command) throws LoofCharacteristicAlreadyExists {
        return coatEffectUseCase.update(id, command);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCoatEffect(@PathVariable Long id) {
        coatEffectUseCase.deleteById(id);
    }
}
