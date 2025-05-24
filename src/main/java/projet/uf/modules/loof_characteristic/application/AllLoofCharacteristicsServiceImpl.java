package projet.uf.modules.loof_characteristic.application;

import projet.uf.modules.loof_characteristic.application.model.AllLoofCharacteristics;
import projet.uf.modules.loof_characteristic.application.port.in.AllLoofCharacteristicsService;
import projet.uf.modules.loof_characteristic.application.port.out.LoofCharacteristicPersistence;
import projet.uf.modules.loof_characteristic.domain.model.*;

public class AllLoofCharacteristicsServiceImpl implements AllLoofCharacteristicsService {
    private final LoofCharacteristicPersistence<Breed> breedPersistencePort;
    private final LoofCharacteristicPersistence<CoatColor> coatColorPersistencePort;
    private final LoofCharacteristicPersistence<CoatEffect> coatEffectPersistencePort;
    private final LoofCharacteristicPersistence<CoatPattern> coatPatternPersistencePort;
    private final LoofCharacteristicPersistence<CoatWhiteMarking> coatWhiteMarkingPersistencePort;
    private final LoofCharacteristicPersistence<PolyType> polyTypePersistencePort;

    public AllLoofCharacteristicsServiceImpl(LoofCharacteristicPersistence<Breed> breedPersistencePort, LoofCharacteristicPersistence<CoatColor> coatColorPersistencePort, LoofCharacteristicPersistence<CoatEffect> coatEffectPersistencePort, LoofCharacteristicPersistence<CoatPattern> coatPatternPersistencePort, LoofCharacteristicPersistence<CoatWhiteMarking> coatWhiteMarkingPersistencePort, LoofCharacteristicPersistence<PolyType> polyTypePersistencePort) {
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
}
