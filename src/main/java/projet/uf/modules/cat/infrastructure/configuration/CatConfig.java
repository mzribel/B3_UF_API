package projet.uf.modules.cat.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projet.uf.modules.breeder.application.port.in.CatteryAuthorizationUseCase;
import projet.uf.modules.breeder.application.port.out.BreederPersistencePort;
import projet.uf.modules.cat.adapter.out.persistence.cat.CatPersistenceAdapter;
import projet.uf.modules.cat.adapter.out.persistence.cat.JpaCatRepository;
import projet.uf.modules.cat.adapter.out.persistence.catcoat.CatCoatPersistenceAdapter;
import projet.uf.modules.cat.adapter.out.persistence.catcoat.JpaCatCoatRepository;
import projet.uf.modules.cat.application.CatAuthorizationService;
import projet.uf.modules.cat.application.CatCoatService;
import projet.uf.modules.cat.application.CatDtoAssembler;
import projet.uf.modules.cat.application.CatService;
import projet.uf.modules.cat.application.ports.in.*;
import projet.uf.modules.cat.application.ports.out.CatCoatPersistencePort;
import projet.uf.modules.cat.application.ports.out.CatPersistencePort;
import projet.uf.modules.cat.application.ports.out.LitterPersistencePort;
import projet.uf.modules.loof_characteristic.application.port.in.AllLoofCharacteristicsUseCase;

@Configuration
public class CatConfig {
    @Bean
    public CatPersistencePort catPersistencePort(JpaCatRepository jpaCatRepository) {
        return new CatPersistenceAdapter(jpaCatRepository);
    }
    @Bean
    public CatCoatPersistencePort catCoatPersistencePort(JpaCatCoatRepository jpaCatCoatRepository) {
        return new CatCoatPersistenceAdapter(jpaCatCoatRepository);
    }

    @Bean
    public CatAuthorizationService catAccessService(CatPersistencePort catPersistencePort, CatteryAuthorizationUseCase catteryAccessUseCase) {
        return new CatAuthorizationService(catPersistencePort, catteryAccessUseCase);
    }

    @Bean
    public CatCoatService catCoatService(CatCoatPersistencePort catCoatPersistencePort, CatPersistencePort catPersistencePort, AllLoofCharacteristicsUseCase allLoofCharacteristicsUseCase) {
        return new CatCoatService(catCoatPersistencePort, catPersistencePort, allLoofCharacteristicsUseCase);
    }

    @Bean
    public CatDtoAssembler catDtoAssembler(
            BreederPersistencePort breederPersistencePort,
            CatCoatUseCase catCoatUseCase,
            LitterPersistencePort litterPersistencePort,
            CatPersistencePort catPersistencePort
    ) {
        return new CatDtoAssembler(
                breederPersistencePort,
                catCoatUseCase,
                litterPersistencePort,
                catPersistencePort);
    }

    @Bean
    public CatService catService(CatPersistencePort catPersistencePort,
                                 CatteryAuthorizationUseCase catteryAccessUseCase,
                                 CatDtoAssembler assembler,
                                 CatAuthorizationUseCase catAccessUseCase,
                                 CatCoatUseCase catCoatUseCase,
                                 LitterAuthorizationUseCase litterAccessUseCase,
                                 CreateLitterUseCase createLitterUseCase) {
        return new CatService(
                catPersistencePort,
                catteryAccessUseCase,
                catAccessUseCase,
                catCoatUseCase,
                assembler,
                litterAccessUseCase,
                createLitterUseCase);
    }
}
