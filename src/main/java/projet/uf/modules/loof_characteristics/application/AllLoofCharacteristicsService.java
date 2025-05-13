package projet.uf.modules.loof_characteristics.application;

import projet.uf.modules.loof_characteristics.application.model.AllLoofCharacteristicsResult;
import projet.uf.modules.loof_characteristics.application.ports.in.GetAllLoofCharacteristicsUseCase;
import projet.uf.modules.loof_characteristics.application.ports.out.LoofCharacteristicPersistencePort;
import projet.uf.modules.loof_characteristics.domain.model.*;

public class AllLoofCharacteristicsService implements GetAllLoofCharacteristicsUseCase {
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
    public AllLoofCharacteristicsResult getAll() {
        return new AllLoofCharacteristicsResult(
                breedPersistencePort.getAll(),
                coatColorPersistencePort.getAll(),
                coatEffectPersistencePort.getAll(),
                coatPatternPersistencePort.getAll(),
                coatWhiteMarkingPersistencePort.getAll(),
                polyTypePersistencePort.getAll()
        );
    }
}
