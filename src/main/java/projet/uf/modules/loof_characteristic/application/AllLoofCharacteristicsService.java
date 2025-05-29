package projet.uf.modules.loof_characteristic.application;

import lombok.AllArgsConstructor;
import projet.uf.modules.loof_characteristic.application.model.AllLoofCharacteristicsDto;
import projet.uf.modules.loof_characteristic.application.port.in.AllLoofCharacteristicsUseCase;
import projet.uf.modules.loof_characteristic.application.port.out.LoofCharacteristicPersistencePort;
import projet.uf.modules.loof_characteristic.domain.model.*;

import java.util.Optional;

@AllArgsConstructor
public class AllLoofCharacteristicsService implements AllLoofCharacteristicsUseCase {
    private final LoofCharacteristicPersistencePort<Breed> breedPersistencePort;
    private final LoofCharacteristicPersistencePort<CoatColor> coatColorPersistencePort;
    private final LoofCharacteristicPersistencePort<CoatEffect> coatEffectPersistencePort;
    private final LoofCharacteristicPersistencePort<CoatPattern> coatPatternPersistencePort;
    private final LoofCharacteristicPersistencePort<CoatWhiteMarking> coatWhiteMarkingPersistencePort;
    private final LoofCharacteristicPersistencePort<PolyType> polyTypePersistencePort;

    @Override
    public AllLoofCharacteristicsDto getAll() {
        return new AllLoofCharacteristicsDto(
                breedPersistencePort.getAll(),
                coatColorPersistencePort.getAll(),
                coatEffectPersistencePort.getAll(),
                coatPatternPersistencePort.getAll(),
                coatWhiteMarkingPersistencePort.getAll(),
                polyTypePersistencePort.getAll()
        );
    }

    @Override
    public Optional<Breed> getBreedById(Long id) {
        return breedPersistencePort.getById(id);
    }

    @Override
    public Optional<CoatColor> getCoatColorById(Long id) {
        return coatColorPersistencePort.getById(id);
    }
    @Override
    public Optional<CoatEffect> getCoatEffectById(Long id) {
        return coatEffectPersistencePort.getById(id);
    }


    @Override
    public Optional<CoatPattern> getCoatPatternById(Long id) {
        return coatPatternPersistencePort.getById(id);
    }


    @Override
    public Optional<CoatWhiteMarking> getCoatWhiteMarkingById(Long id) {
        return coatWhiteMarkingPersistencePort.getById(id);
    }

    @Override
    public Optional<PolyType> getPolyTypeById(Long id) {
        return polyTypePersistencePort.getById(id);
    }

}
