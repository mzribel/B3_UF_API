package projet.uf.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import projet.uf.adapters.out.persistence.TemplateEntity;
import projet.uf.exceptions.TemplateException;
import projet.uf.domain.model.Template;
import projet.uf.adapters.out.persistence.JpaTemplateRepository;
import java.util.List;

@Service
public class TemplateService {

    private final JpaTemplateRepository templateRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public TemplateService(final JpaTemplateRepository templateRepository) {
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