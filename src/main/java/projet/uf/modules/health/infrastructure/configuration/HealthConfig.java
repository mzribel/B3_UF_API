package projet.uf.modules.health.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projet.uf.modules.cat.application.ports.in.CatAuthorizationUseCase;
import projet.uf.modules.health.adapter.out.persistence.gestationhealthlog.GestationHealthLogPersistenceAdapter;
import projet.uf.modules.health.adapter.out.persistence.gestationhealthlog.JpaGestationHealthLogRepository;
import projet.uf.modules.health.adapter.out.persistence.healthlog.HealthLogPersistenceAdapter;
import projet.uf.modules.health.adapter.out.persistence.healthlog.JpaHealthLogRepository;
import projet.uf.modules.health.adapter.out.persistence.kittenhealthlog.KittenHealthLogPersistenceAdapter;
import projet.uf.modules.health.adapter.out.persistence.kittenhealthlog.JpaKittenHealthLogRepository;
import projet.uf.modules.health.application.HealthLogAuthorizationService;
import projet.uf.modules.health.application.HealthLogService;
import projet.uf.modules.health.application.port.in.HealthLogAuthorizationUseCase;
import projet.uf.modules.health.application.port.in.HealthLogUseCase;
import projet.uf.modules.health.application.port.out.GestationHealthLogPersistencePort;
import projet.uf.modules.health.application.port.out.HealthLogPersistencePort;
import projet.uf.modules.health.application.port.out.KittenHealthLogPersistencePort;

@Configuration
public class HealthConfig {
    @Bean
    public HealthLogPersistencePort healthLogPersistencePort(JpaHealthLogRepository jpaHealthLogRepository) {
        return new HealthLogPersistenceAdapter(jpaHealthLogRepository);
    }

    @Bean
    public KittenHealthLogPersistencePort kittenHealthLogPersistencePort(JpaKittenHealthLogRepository jpaKittenHealthLogRepository) {
        return new KittenHealthLogPersistenceAdapter(jpaKittenHealthLogRepository);
    }

    @Bean
    public GestationHealthLogPersistencePort gestationHealthLogPersistencePort(JpaGestationHealthLogRepository jpaGestationHealthLogRepository) {
        return new GestationHealthLogPersistenceAdapter(jpaGestationHealthLogRepository);
    }

    @Bean
    public HealthLogAuthorizationService healthLogAccessService(
            HealthLogPersistencePort healthLogPersistencePort,
            CatAuthorizationUseCase catAccessUseCase
    ) {
        return new HealthLogAuthorizationService(healthLogPersistencePort, catAccessUseCase);
    }

    @Bean
    public HealthLogUseCase healthLogUseCase(
            HealthLogPersistencePort healthLogPersistencePort,
            KittenHealthLogPersistencePort kittenHealthLogPersistencePort,
            GestationHealthLogPersistencePort gestationHealthLogPersistencePort,
            CatAuthorizationUseCase catAccessUseCase,
            HealthLogAuthorizationUseCase healthLogAccessUseCase) {
        return new HealthLogService(
                healthLogPersistencePort,
                kittenHealthLogPersistencePort,
                gestationHealthLogPersistencePort,
                catAccessUseCase,
                healthLogAccessUseCase
        );
    }
}