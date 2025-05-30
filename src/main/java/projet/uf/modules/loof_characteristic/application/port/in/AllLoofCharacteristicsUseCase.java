package projet.uf.modules.loof_characteristic.application.port.in;

import projet.uf.modules.loof_characteristic.application.model.AllLoofCharacteristicsDto;
import projet.uf.modules.loof_characteristic.domain.model.*;

import java.util.Optional;

public interface AllLoofCharacteristicsUseCase {
    AllLoofCharacteristicsDto getAll();
    Optional<Breed> getBreedById(Long id);
    Optional<CoatColor> getCoatColorById(Long id);
    Optional<CoatEffect> getCoatEffectById(Long id);
    Optional<CoatPattern> getCoatPatternById(Long id);
    Optional<CoatWhiteMarking> getCoatWhiteMarkingById(Long id);
    Optional<PolyType> getPolyTypeById(Long id);
}
