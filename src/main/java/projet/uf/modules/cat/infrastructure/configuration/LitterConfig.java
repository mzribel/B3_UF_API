package projet.uf.modules.cat.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projet.uf.modules.breeder.application.port.in.CatteryAuthorizationUseCase;
import projet.uf.modules.cat.adapter.out.persistence.litter.JpaLitterRepository;
import projet.uf.modules.cat.adapter.out.persistence.litter.LitterPersistenceAdapter;
import projet.uf.modules.cat.application.LitterAuthorizationService;
import projet.uf.modules.cat.application.LitterService;
import projet.uf.modules.cat.application.ports.in.CatAuthorizationUseCase;
import projet.uf.modules.cat.application.ports.in.LitterAuthorizationUseCase;
import projet.uf.modules.cat.application.ports.out.LitterPersistencePort;

@Configuration
public class LitterConfig {
    @Bean
    LitterPersistencePort litterPersistencePort(JpaLitterRepository jpaLitterRepository) {
        return new LitterPersistenceAdapter(jpaLitterRepository);
    }

    @Bean
    LitterAuthorizationService litterAccessService(LitterPersistencePort litterPersistencePort, CatteryAuthorizationUseCase catteryAccessUseCase) {
        return new LitterAuthorizationService(litterPersistencePort, catteryAccessUseCase);
    }

    @Bean
    LitterService litterService(
            LitterPersistencePort litterPersistencePort,
            LitterAuthorizationUseCase litterAccessUseCase,
            CatteryAuthorizationUseCase catteryAccessUseCase,
            CatAuthorizationUseCase catAccessUseCase
            ) {
        return new LitterService(
                litterPersistencePort,
                litterAccessUseCase,
                catteryAccessUseCase,
                catAccessUseCase
        );
    }
}
