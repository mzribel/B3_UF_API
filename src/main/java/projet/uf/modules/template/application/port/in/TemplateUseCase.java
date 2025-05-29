package projet.uf.modules.template.application.port.in;

import projet.uf.modules.template.domain.model.Template;
import projet.uf.modules.template.exception.TemplateException;

import java.util.List;

public interface TemplateUseCase {
    List<Template> getTemplateByName(final String name, final boolean accurate) throws TemplateException;
}
