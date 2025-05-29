package projet.uf.modules.template.application.port.out;

import projet.uf.modules.template.domain.model.Template;

import java.util.List;

public interface TemplatePersistencePort {
    List<Template> findByName(String name);
    List<Template> findByNameLike(String name);
    Template save(Template template);
}
