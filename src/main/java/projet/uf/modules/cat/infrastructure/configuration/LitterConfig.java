package projet.uf.modules.cat.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projet.uf.modules.breeder.application.port.in.CatteryAccessUseCase;
import projet.uf.modules.cat.adapter.out.persistence.litter.JpaLitterRepository;
import projet.uf.modules.cat.adapter.out.persistence.litter.LitterPersistenceAdapter;
import projet.uf.modules.cat.application.LitterAccessService;
import projet.uf.modules.cat.application.LitterService;
import projet.uf.modules.cat.application.ports.in.CatAccessUseCase;
import projet.uf.modules.cat.application.ports.in.LitterAccessUseCase;
import projet.uf.modules.cat.application.ports.out.LitterPersistencePort;

@Configuration
public class LitterConfig {
    @Bean
    LitterPersistencePort litterPersistencePort(JpaLitterRepository jpaLitterRepository) {
        return new LitterPersistenceAdapter(jpaLitterRepository);
    }

    @Bean
    LitterAccessService litterAccessService(LitterPersistencePort litterPersistencePort, CatteryAccessUseCase catteryAccessUseCase) {
        return new LitterAccessService(litterPersistencePort, catteryAccessUseCase);
    }

    @Bean
    LitterService litterService(
            LitterPersistencePort litterPersistencePort,
            LitterAccessUseCase litterAccessUseCase,
            CatteryAccessUseCase catteryAccessUseCase,
            CatAccessUseCase catAccessUseCase
            ) {
        return new LitterService(
                litterPersistencePort,
                litterAccessUseCase,
                catteryAccessUseCase,
                catAccessUseCase
        );
    }
}
