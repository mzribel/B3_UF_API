package projet.uf.modules.loof_characteristics.adapters.in.rest;

import org.springframework.web.bind.annotation.*;
import projet.uf.modules.loof_characteristics.application.ports.in.CreateLoofCharacteristicCommand;
import projet.uf.modules.loof_characteristics.application.ports.in.CreateLoofCharacteristicUseCase;
import projet.uf.modules.loof_characteristics.application.ports.in.GetLoofCharacteristicUseCase;
import projet.uf.modules.loof_characteristics.domain.model.Breed;

import java.util.List;

@RestController
@RequestMapping("/loof/characteristics/breeds")
public class BreedController {
    final GetLoofCharacteristicUseCase<Breed> getBreedUseCase;
    final CreateLoofCharacteristicUseCase<Breed> createBreedUseCase;

    public BreedController(GetLoofCharacteristicUseCase<Breed> getLoofCharacteristicUseCase, CreateLoofCharacteristicUseCase<Breed> createLoofCharacteristicUseCase) {
        this.getBreedUseCase = getLoofCharacteristicUseCase;
        this.createBreedUseCase = createLoofCharacteristicUseCase;
    }

    @GetMapping
    public List<Breed> getAll() {
        return getBreedUseCase.getAll();
    }
    @PostMapping
    public Breed createBreed(@RequestBody CreateLoofCharacteristicCommand command) throws Exception {
        return createBreedUseCase.create(command);
    }
}
