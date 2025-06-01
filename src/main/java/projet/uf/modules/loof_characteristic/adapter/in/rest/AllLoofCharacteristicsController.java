package projet.uf.modules.loof_characteristic.adapter.in.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projet.uf.modules.loof_characteristic.application.model.AllLoofCharacteristicsDto;
import projet.uf.modules.loof_characteristic.application.port.in.AllLoofCharacteristicsUseCase;

@RestController
@AllArgsConstructor
@RequestMapping("/loof/characteristics")
@Tag(name = "Caractéristiques LOOF", description = "Gestion des caractéristiques légales du LOOF")
public class AllLoofCharacteristicsController {
    final AllLoofCharacteristicsUseCase getAllLoofCharacteristicsUseCase;

    @GetMapping
    public AllLoofCharacteristicsDto getAllLoofCharacteristics() {
        return getAllLoofCharacteristicsUseCase.getAll();
    }
}
