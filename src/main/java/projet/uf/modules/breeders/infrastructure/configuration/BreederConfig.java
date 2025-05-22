package projet.uf.modules.breeders.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import projet.uf.modules.breeders.adapters.out.persistence.breeder.BreederPersistenceAdapter;
import projet.uf.modules.breeders.adapters.out.persistence.breeder.JpaBreederRepository;
import projet.uf.modules.breeders.application.ports.BreederServiceImpl;
import projet.uf.modules.breeders.application.ports.in.BreederService;
import projet.uf.modules.breeders.application.ports.out.BreederPersistencePort;

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
