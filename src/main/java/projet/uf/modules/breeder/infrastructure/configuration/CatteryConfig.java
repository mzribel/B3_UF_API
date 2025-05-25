package projet.uf.modules.breeder.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projet.uf.modules.breeder.adapter.out.persistence.cattery.CatteryPersistenceAdapter;
import projet.uf.modules.breeder.adapter.out.persistence.cattery.JpaCatteryRepository;
import projet.uf.modules.breeder.adapter.out.persistence.catteryuser.CatteryUserPersistenceAdapter;
import projet.uf.modules.breeder.adapter.out.persistence.catteryuser.JpaCatteryUserRepository;
import projet.uf.modules.breeder.application.port.CatteryService;
import projet.uf.modules.breeder.application.port.out.CatteryPersistencePort;
import projet.uf.modules.breeder.application.port.out.CatteryUserPersistencePort;
import projet.uf.modules.breeder.application.port.out.UserAccessPort;

@Configuration
public class CatteryConfig {
    @Bean
    public CatteryPersistencePort catteryPersistencePort(JpaCatteryRepository jpaCatteryRepository) {
        return new CatteryPersistenceAdapter(jpaCatteryRepository);
    }
    @Bean
    public CatteryUserPersistencePort catteryUserPersistencePort(JpaCatteryUserRepository jpaCatteryUserRepository) {
        return new CatteryUserPersistenceAdapter(jpaCatteryUserRepository);
    }
    @Bean
    public CatteryService catteryService(CatteryPersistencePort catteryPersistencePort, CatteryUserPersistencePort catteryUserPersistencePort, UserAccessPort userAccessPort) {
        return new CatteryService(catteryPersistencePort, catteryUserPersistencePort, userAccessPort);
    }
}
