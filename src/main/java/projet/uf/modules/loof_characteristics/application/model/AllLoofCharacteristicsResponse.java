package projet.uf.modules.loof_characteristics.application.model;

import projet.uf.modules.loof_characteristics.domain.model.*;

import java.util.List;

public record AllLoofCharacteristicsResponse(
    List<Breed> breeds,
    List<CoatColor> coatColors,
    List<CoatEffect> coatEffects,
    List<CoatPattern> coatPatterns,
    List<CoatWhiteMarking> coatWhiteMarkings,
    List<PolyType> polyTypes
){};
