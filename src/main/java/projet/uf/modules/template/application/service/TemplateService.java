package projet.uf.modules.template.application.service;

import lombok.AllArgsConstructor;
import projet.uf.modules.template.application.port.in.TemplateUseCase;
import projet.uf.modules.template.application.port.out.TemplatePersistencePort;
import projet.uf.modules.template.exception.TemplateException;
import projet.uf.modules.template.domain.model.Template;
import java.util.List;

@AllArgsConstructor
public class TemplateService implements TemplateUseCase {

    private final TemplatePersistencePort templatePersistencePort;

    public List<Template> getTemplateByName(final String name, final boolean accurate) throws TemplateException {
        try {
            return accurate
                ? templatePersistencePort.findByName(name)
                : templatePersistencePort.findByNameLike(name);
        } catch (final Exception e) {
            throw new TemplateException("Error retrieving template: " + e.getMessage(), e);
        }
    }
}