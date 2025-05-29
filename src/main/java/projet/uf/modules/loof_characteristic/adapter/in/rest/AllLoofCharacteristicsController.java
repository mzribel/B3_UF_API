package projet.uf.modules.loof_characteristic.adapter.in.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projet.uf.modules.loof_characteristic.application.model.AllLoofCharacteristics;
import projet.uf.modules.loof_characteristic.application.port.in.AllLoofCharacteristicsUseCase;

@RestController
@RequestMapping({"/loof/characteristics/", "/loof/characteristics"})
public class AllLoofCharacteristicsController {
    final AllLoofCharacteristicsUseCase getAllLoofCharacteristicsUseCase;

    public AllLoofCharacteristicsController(AllLoofCharacteristicsUseCase getAllLoofCharacteristicsUseCase) {
        this.getAllLoofCharacteristicsUseCase = getAllLoofCharacteristicsUseCase;
    }

    @GetMapping
    public AllLoofCharacteristics getAllLoofCharacteristics() {
        return getAllLoofCharacteristicsUseCase.getAll();
    }
}
