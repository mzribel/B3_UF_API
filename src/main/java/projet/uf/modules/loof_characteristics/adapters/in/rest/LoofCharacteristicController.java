package projet.uf.modules.loof_characteristics.adapters.in.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projet.uf.modules.loof_characteristics.application.model.AllLoofCharacteristicsResult;
import projet.uf.modules.loof_characteristics.application.ports.in.GetAllLoofCharacteristicsUseCase;

@RestController
@RequestMapping("/loof/characteristics")
public class LoofCharacteristicController {
    final GetAllLoofCharacteristicsUseCase getAllLoofCharacteristicsUseCase;

    public LoofCharacteristicController(GetAllLoofCharacteristicsUseCase getAllLoofCharacteristicsUseCase) {
        this.getAllLoofCharacteristicsUseCase = getAllLoofCharacteristicsUseCase;
    }

    @GetMapping
    public AllLoofCharacteristicsResult getAllLoofCharacteristics() {
        return getAllLoofCharacteristicsUseCase.getAll();
    }
}
