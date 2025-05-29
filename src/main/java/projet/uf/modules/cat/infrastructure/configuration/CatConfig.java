package projet.uf.modules.cat.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projet.uf.modules.cat.adapter.out.persistence.CatPersistenceAdapter;
import projet.uf.modules.cat.adapter.out.persistence.JpaCatRepository;
import projet.uf.modules.cat.application.CatService;
import projet.uf.modules.cat.application.ports.out.CatPersistencePort;

@Configuration
public class CatConfig {
    @Bean
    public CatPersistencePort catPersistencePort(JpaCatRepository jpaCatRepository) {
        return new CatPersistenceAdapter(jpaCatRepository);
    }

    @Bean
    public CatService catService(CatPersistencePort catPersistencePort) {
        return new CatService(catPersistencePort);
    }
}
