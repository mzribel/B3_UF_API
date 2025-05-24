package projet.uf.modules.breeder.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projet.uf.modules.breeder.adapter.out.persistence.breeder.BreederPersistenceImpl;
import projet.uf.modules.breeder.adapter.out.persistence.breeder.JpaBreederRepository;
import projet.uf.modules.breeder.application.port.BreederServiceImpl;
import projet.uf.modules.breeder.application.port.out.BreederPersistence;

@Configuration
public class BreederConfig {
    @Bean
    public BreederPersistence breederPersistencePort(JpaBreederRepository jpaBreederRepository) {
        return new BreederPersistenceImpl(jpaBreederRepository);
    }
    @Bean
    public BreederServiceImpl breederService(BreederPersistence breederPersistencePort) {
        return new BreederServiceImpl(breederPersistencePort);
    }
}
