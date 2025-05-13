package projet.uf.template.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import projet.uf.template.adapters.out.persistence.TemplateEntity;
import projet.uf.template.exceptions.TemplateException;
import projet.uf.template.domain.model.Template;
import projet.uf.template.adapters.out.persistence.JpaTemplateRepository;
import java.util.List;

@Service
public class TemplateUseCaseService {

    private final JpaTemplateRepository templateRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public TemplateUseCaseService(final JpaTemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public List<Template> getTemplateByName(final String name, final boolean accurate)
            throws TemplateException {
        try {
            return templateEntityListToTemplateList(accurate
                    ? templateRepository.findByName(name)
                    : templateRepository.findByNameLike(name));
        } catch (final Exception e) {
            throw new TemplateException("Error retrieving template: " + e.getMessage(), e);
        }
    }

    private List<Template> templateEntityListToTemplateList(List<TemplateEntity> templateEntities) {
        return templateEntities.stream()
                .map(Template::from)
                .toList();
    }


}