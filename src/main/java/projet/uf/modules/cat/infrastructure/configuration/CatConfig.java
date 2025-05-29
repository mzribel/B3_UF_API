package projet.uf.modules.cat.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projet.uf.modules.breeder.application.port.in.CatteryAccessUseCase;
import projet.uf.modules.cat.adapter.out.persistence.CatPersistenceAdapter;
import projet.uf.modules.cat.adapter.out.persistence.JpaCatRepository;
import projet.uf.modules.cat.application.CatAccessService;
import projet.uf.modules.cat.application.CatService;
import projet.uf.modules.cat.application.ports.in.CatAccessUseCase;
import projet.uf.modules.cat.application.ports.out.CatPersistencePort;

@Configuration
public class CatConfig {
    @Bean
    public CatPersistencePort catPersistencePort(JpaCatRepository jpaCatRepository) {
        return new CatPersistenceAdapter(jpaCatRepository);
    }

    @Bean
    public CatAccessService catAccessService(CatPersistencePort catPersistencePort, CatteryAccessUseCase catteryAccessUseCase) {
        return new CatAccessService(catPersistencePort, catteryAccessUseCase);
    }

    @Bean
    public CatService catService(CatPersistencePort catPersistencePort, CatteryAccessUseCase catteryAccessUseCase, CatAccessUseCase catAccessUseCase) {
        return new CatService(catPersistencePort, catteryAccessUseCase, catAccessUseCase);
    }
}
