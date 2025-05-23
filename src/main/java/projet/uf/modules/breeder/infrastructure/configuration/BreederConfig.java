package projet.uf.modules.breeder.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projet.uf.modules.breeder.adapter.out.persistence.breeder.BreederPersistenceAdapter;
import projet.uf.modules.breeder.adapter.out.persistence.breeder.JpaBreederRepository;
import projet.uf.modules.breeder.application.port.BreederServiceImpl;
import projet.uf.modules.breeder.application.port.out.BreederPersistencePort;

@Configuration
public class BreederConfig {
    @Bean
    public BreederPersistencePort breederPersistencePort(JpaBreederRepository jpaBreederRepository) {
        return new BreederPersistenceAdapter(jpaBreederRepository);
    }
    @Bean
    public BreederServiceImpl breederService(BreederPersistencePort breederPersistencePort) {
        return new BreederServiceImpl(breederPersistencePort);
    }
}
