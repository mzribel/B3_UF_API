package projet.uf.modules.cat.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projet.uf.modules.cat.adapter.out.persistence.CatPersistenceImpl;
import projet.uf.modules.cat.adapter.out.persistence.JpaCatRepository;
import projet.uf.modules.cat.application.CatServiceImpl;
import projet.uf.modules.cat.application.ports.out.CatPersistence;

@Configuration
public class CatConfig {
    @Bean
    public CatPersistence catPersistencePort(JpaCatRepository jpaCatRepository) {
        return new CatPersistenceImpl(jpaCatRepository);
    }

    @Bean
    public CatServiceImpl catService(CatPersistence catPersistencePort) {
        return new CatServiceImpl(catPersistencePort);
    }
}
