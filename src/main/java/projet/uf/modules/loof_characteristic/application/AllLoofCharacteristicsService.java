package projet.uf.modules.loof_characteristic.application;

import projet.uf.modules.loof_characteristic.application.model.AllLoofCharacteristics;
import projet.uf.modules.loof_characteristic.application.port.in.AllLoofCharacteristicsUseCase;
import projet.uf.modules.loof_characteristic.application.port.out.LoofCharacteristicPersistencePort;
import projet.uf.modules.loof_characteristic.domain.model.*;

import java.util.Optional;

public class AllLoofCharacteristicsService implements AllLoofCharacteristicsUseCase {
    private final LoofCharacteristicPersistencePort<Breed> breedPersistencePort;
    private final LoofCharacteristicPersistencePort<CoatColor> coatColorPersistencePort;
    private final LoofCharacteristicPersistencePort<CoatEffect> coatEffectPersistencePort;
    private final LoofCharacteristicPersistencePort<CoatPattern> coatPatternPersistencePort;
    private final LoofCharacteristicPersistencePort<CoatWhiteMarking> coatWhiteMarkingPersistencePort;
    private final LoofCharacteristicPersistencePort<PolyType> polyTypePersistencePort;

    public AllLoofCharacteristicsService(LoofCharacteristicPersistencePort<Breed> breedPersistencePort, LoofCharacteristicPersistencePort<CoatColor> coatColorPersistencePort, LoofCharacteristicPersistencePort<CoatEffect> coatEffectPersistencePort, LoofCharacteristicPersistencePort<CoatPattern> coatPatternPersistencePort, LoofCharacteristicPersistencePort<CoatWhiteMarking> coatWhiteMarkingPersistencePort, LoofCharacteristicPersistencePort<PolyType> polyTypePersistencePort) {
        this.breedPersistencePort = breedPersistencePort;
        this.coatColorPersistencePort = coatColorPersistencePort;
        this.coatEffectPersistencePort = coatEffectPersistencePort;
        this.coatPatternPersistencePort = coatPatternPersistencePort;
        this.coatWhiteMarkingPersistencePort = coatWhiteMarkingPersistencePort;
        this.polyTypePersistencePort = polyTypePersistencePort;
    }

    @Override
    public AllLoofCharacteristics getAll() {
        return new AllLoofCharacteristics(
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
