package projet.uf.modules.loof_characteristic.adapter.in.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projet.uf.modules.loof_characteristic.application.model.AllLoofCharacteristics;
import projet.uf.modules.loof_characteristic.application.port.in.AllLoofCharacteristicsService;

@RestController
@RequestMapping({"/loof/characteristics/", "/loof/characteristics"})
public class LoofCharacteristicController {
    final AllLoofCharacteristicsService getAllLoofCharacteristicsUseCase;

    public LoofCharacteristicController(AllLoofCharacteristicsService getAllLoofCharacteristicsUseCase) {
        this.getAllLoofCharacteristicsUseCase = getAllLoofCharacteristicsUseCase;
    }

    @GetMapping
    public AllLoofCharacteristics getAllLoofCharacteristics() {
        return getAllLoofCharacteristicsUseCase.getAll();
    }
}
