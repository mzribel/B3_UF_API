package projet.uf.modules.loof_characteristic.adapter.in.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.loof_characteristic.application.port.in.CreateLoofCharacteristicCommand;
import projet.uf.modules.loof_characteristic.application.port.in.LoofCharacteristicUseCase;
import projet.uf.modules.loof_characteristic.domain.model.CoatWhiteMarking;
import projet.uf.modules.loof_characteristic.exception.LoofCharacteristicAlreadyExists;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping({"/loof/characteristics/coat-white-markings/", "/loof/characteristics/coat-white-markings"})
public class CoatWhiteMarkingController {
    final LoofCharacteristicUseCase<CoatWhiteMarking> CoatWhiteMarkingUseCase;

    @GetMapping
    public List<CoatWhiteMarking> getAll() {
        return CoatWhiteMarkingUseCase.getAll();
    }

    @GetMapping({"/{id}/", "/{id}"})
    public Optional<CoatWhiteMarking> getById(@PathVariable Long id) {
        return Optional.ofNullable(CoatWhiteMarkingUseCase.getById(id)
            .orElseThrow(() -> new ApiException("Aucune couleur de robe (coat color) trouvée avec l'id " + id, HttpStatus.NOT_FOUND)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public CoatWhiteMarking createCoatWhiteMarking(@Valid @RequestBody CreateLoofCharacteristicCommand command) throws LoofCharacteristicAlreadyExists {
        return CoatWhiteMarkingUseCase.create(command);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping({"/{id}/", "/{id}"})
    public CoatWhiteMarking updateCoatWhiteMarking(@PathVariable Long id, @Valid @RequestBody CreateLoofCharacteristicCommand command) throws LoofCharacteristicAlreadyExists {
        return CoatWhiteMarkingUseCase.update(id, command);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping({"/{id}/", "/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCoatWhiteMarking(@PathVariable Long id) {
        CoatWhiteMarkingUseCase.deleteById(id);
    }
}
