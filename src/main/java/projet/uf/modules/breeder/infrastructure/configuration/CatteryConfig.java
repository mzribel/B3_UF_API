package projet.uf.modules.breeder.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projet.uf.modules.breeder.adapter.out.persistence.cattery.CatteryEntity;
import projet.uf.modules.breeder.adapter.out.persistence.cattery.CatteryPersistenceAdapter;
import projet.uf.modules.breeder.adapter.out.persistence.cattery.JpaCatteryRepository;
import projet.uf.modules.breeder.adapter.out.persistence.catteryuser.CatteryUserPersistenceAdapter;
import projet.uf.modules.breeder.adapter.out.persistence.catteryuser.JpaCatteryUserRepository;
import projet.uf.modules.breeder.application.CatteryAccessService;
import projet.uf.modules.breeder.application.CatteryService;
import projet.uf.modules.breeder.application.port.in.BreederUseCase;
import projet.uf.modules.breeder.application.port.out.BreederPersistencePort;
import projet.uf.modules.breeder.application.port.out.CatteryPersistencePort;
import projet.uf.modules.breeder.application.port.out.CatteryUserPersistencePort;
import projet.uf.modules.user.adapter.out.persistence.JpaUserRepository;
import projet.uf.modules.user.application.port.out.UserPersistencePort;

@Configuration
public class CatteryConfig {
    @Bean
    public CatteryPersistencePort catteryPersistencePort(JpaCatteryRepository jpaCatteryRepository) {
        return new CatteryPersistenceAdapter(jpaCatteryRepository);
    }
    @Bean
    public CatteryUserPersistencePort catteryUserPersistencePort(JpaCatteryUserRepository jpaCatteryUserRepository, JpaUserRepository jpaUserRepository, JpaCatteryRepository jpaCatteryRepository ) {
        return new CatteryUserPersistenceAdapter(jpaCatteryUserRepository, jpaUserRepository, jpaCatteryRepository);
    }

    @Bean
    public CatteryService catteryService(CatteryPersistencePort catteryPersistencePort, CatteryUserPersistencePort catteryUserPersistencePort, BreederPersistencePort breederPersistencePort, BreederUseCase breederUseCase, UserPersistencePort userPersistencePort) {
        return new CatteryService(catteryPersistencePort, catteryUserPersistencePort, breederPersistencePort, breederUseCase, userPersistencePort);
    }

    @Bean
    CatteryAccessService catteryAccessService(
            CatteryPersistencePort catteryPersistencePort,
            CatteryUserPersistencePort catteryUserPersistencePort
    ) {
        return new CatteryAccessService(catteryPersistencePort, catteryUserPersistencePort);
    }
}
