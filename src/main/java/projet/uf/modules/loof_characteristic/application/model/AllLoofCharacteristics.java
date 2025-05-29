package projet.uf.modules.loof_characteristic.application.model;

import projet.uf.modules.loof_characteristic.domain.model.*;

import java.util.List;

public record AllLoofCharacteristics(
    List<Breed> breeds,
    List<CoatColor> coatColors,
    List<CoatEffect> coatEffects,
    List<CoatPattern> coatPatterns,
    List<CoatWhiteMarking> coatWhiteMarkings,
    List<PolyType> polyTypes
){}
