package projet.uf.modules.template.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projet.uf.modules.template.adapter.out.persistence.JpaTemplateRepository;
import projet.uf.modules.template.adapter.out.persistence.TemplatePersistenceAdapter;
import projet.uf.modules.template.application.port.out.TemplatePersistencePort;
import projet.uf.modules.template.application.service.TemplateService;

@Configuration
public class TemplateConfig {
    @Bean
    public TemplatePersistenceAdapter templatePersistenceAdapter(JpaTemplateRepository repository) {
        return new TemplatePersistenceAdapter(repository);
    }

    @Bean
    public TemplateService templateService(TemplatePersistencePort templatePersistencePort) {
        return new TemplateService(templatePersistencePort);
    }
}
