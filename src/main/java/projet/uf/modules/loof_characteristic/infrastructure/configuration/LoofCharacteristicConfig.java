package projet.uf.modules.loof_characteristic.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projet.uf.modules.loof_characteristic.adapter.out.persistence.LoofCharacteristicAdapter;
import projet.uf.modules.loof_characteristic.adapter.out.persistence.breed.BreedEntityMapper;
import projet.uf.modules.loof_characteristic.adapter.out.persistence.breed.JpaBreedRepository;
import projet.uf.modules.loof_characteristic.adapter.out.persistence.coat_color.CoatColorEntityMapper;
import projet.uf.modules.loof_characteristic.adapter.out.persistence.coat_color.JpaCoatColorRepository;
import projet.uf.modules.loof_characteristic.adapter.out.persistence.coat_effect.CoatEffectEntityMapper;
import projet.uf.modules.loof_characteristic.adapter.out.persistence.coat_effect.JpaCoatEffectRepository;
import projet.uf.modules.loof_characteristic.adapter.out.persistence.coat_pattern.CoatPatternEntityMapper;
import projet.uf.modules.loof_characteristic.adapter.out.persistence.coat_pattern.JpaCoatPatternRepository;
import projet.uf.modules.loof_characteristic.adapter.out.persistence.coat_white_marking.CoatWhiteMarkingEntityMapper;
import projet.uf.modules.loof_characteristic.adapter.out.persistence.coat_white_marking.JpaCoatWhiteMarkingRepository;
import projet.uf.modules.loof_characteristic.adapter.out.persistence.poly_type.JpaPolyTypeRepository;
import projet.uf.modules.loof_characteristic.adapter.out.persistence.poly_type.PolyTypeEntityMapper;
import projet.uf.modules.loof_characteristic.application.AllLoofCharacteristicsService;
import projet.uf.modules.loof_characteristic.application.LoofCharacteristicService;
import projet.uf.modules.loof_characteristic.application.mapper.*;
import projet.uf.modules.loof_characteristic.application.port.out.LoofCharacteristicPersistencePort;
import projet.uf.modules.loof_characteristic.domain.model.*;

@Configuration
public class LoofCharacteristicConfig {
    // ALL
    @Bean
    public AllLoofCharacteristicsService allLoofCharacteristicsService(
            LoofCharacteristicPersistencePort<Breed> breedPort,
            LoofCharacteristicPersistencePort<CoatColor> coatColorPort,
            LoofCharacteristicPersistencePort<CoatEffect> coatEffectPort,
            LoofCharacteristicPersistencePort<CoatPattern> coatPatternPort,
            LoofCharacteristicPersistencePort<CoatWhiteMarking> coatWhiteMarkingPort,
            LoofCharacteristicPersistencePort<PolyType> polyTypePort
    ) {
        return new AllLoofCharacteristicsService(
                breedPort,
                coatColorPort,
                coatEffectPort,
                coatPatternPort,
                coatWhiteMarkingPort,
                polyTypePort
        );
    }

    // BREEDS
    @Bean
    public BreedCommandMapper breedCommandMapper() {
        return new BreedCommandMapper();
    }
    @Bean
    public LoofCharacteristicPersistencePort<Breed> breedPersistencePort(
            JpaBreedRepository repository,
            BreedEntityMapper mapper) {
        return new LoofCharacteristicAdapter<>(repository, mapper);
    }
    @Bean
    public LoofCharacteristicService<Breed> breedService(
            LoofCharacteristicPersistencePort<Breed> persistencePort,
            LoofCharacteristicCommandMapper<Breed> mapper) {
        return new LoofCharacteristicService<>(persistencePort, mapper);
    }

    // COAT COLORS
    @Bean
    public CoatColorCommandMapper coatColorCommandMapper() {
        return new CoatColorCommandMapper();
    }
    @Bean
    public LoofCharacteristicPersistencePort<CoatColor> coatColorPersistencePort(
            JpaCoatColorRepository repository,
            CoatColorEntityMapper mapper) {
        return new LoofCharacteristicAdapter<>(repository, mapper);
    }
    @Bean
    public LoofCharacteristicService<CoatColor> coatColorService(
            LoofCharacteristicPersistencePort<CoatColor> persistencePort,
            LoofCharacteristicCommandMapper<CoatColor> mapper) {
        return new LoofCharacteristicService<>(persistencePort, mapper);
    }

    // COAT PATTERNS
    @Bean
    public CoatPatternCommandMapper coatPatternCommandMapper() {
        return new CoatPatternCommandMapper();
    }
    @Bean
    public LoofCharacteristicPersistencePort<CoatPattern> coatPatternPersistencePort(
            JpaCoatPatternRepository repository,
            CoatPatternEntityMapper mapper) {
        return new LoofCharacteristicAdapter<>(repository, mapper);
    }
    @Bean
    public LoofCharacteristicService<CoatPattern> coatPatternService(
            LoofCharacteristicPersistencePort<CoatPattern> persistencePort,
            LoofCharacteristicCommandMapper<CoatPattern> mapper) {
        return new LoofCharacteristicService<>(persistencePort, mapper);
    }

    // COAT EFFECTS
    @Bean
    public CoatEffectCommandMapper coatEffectCommandMapper() {
        return new CoatEffectCommandMapper();
    }
    @Bean
    public LoofCharacteristicPersistencePort<CoatEffect> coatEffectPersistencePort(
            JpaCoatEffectRepository repository,
            CoatEffectEntityMapper mapper) {
        return new LoofCharacteristicAdapter<>(repository, mapper);
    }
    @Bean
    public LoofCharacteristicService<CoatEffect> coatEffectService(
            LoofCharacteristicPersistencePort<CoatEffect> persistencePort,
            LoofCharacteristicCommandMapper<CoatEffect> mapper) {
        return new LoofCharacteristicService<>(persistencePort, mapper);
    }

    // COAT WHITE MARKINGS
    @Bean
    public CoatWhiteMarkingCommandMapper coatWhiteMarkingCommandMapper() {
        return new CoatWhiteMarkingCommandMapper();
    }
    @Bean
    public LoofCharacteristicPersistencePort<CoatWhiteMarking> coatWhiteMarkingPersistencePort(
            JpaCoatWhiteMarkingRepository repository,
            CoatWhiteMarkingEntityMapper mapper) {
        return new LoofCharacteristicAdapter<>(repository, mapper);
    }
    @Bean
    public LoofCharacteristicService<CoatWhiteMarking> coatWhiteMarkingService(
            LoofCharacteristicPersistencePort<CoatWhiteMarking> persistencePort,
            LoofCharacteristicCommandMapper<CoatWhiteMarking> mapper) {
        return new LoofCharacteristicService<>(persistencePort, mapper);
    }

    // POLY TYPES
    @Bean
    public PolyTypeCommandMapper polyTypeCommandMapper() {
        return new PolyTypeCommandMapper();
    }
    @Bean
    public LoofCharacteristicPersistencePort<PolyType> polyTypePersistencePort(
            JpaPolyTypeRepository repository,
            PolyTypeEntityMapper mapper) {
        return new LoofCharacteristicAdapter<>(repository, mapper);
    }
    @Bean
    public LoofCharacteristicService<PolyType> polyTypeService(
            LoofCharacteristicPersistencePort<PolyType> persistencePort,
            LoofCharacteristicCommandMapper<PolyType> mapper) {
        return new LoofCharacteristicService<>(persistencePort, mapper);
    }

}
