package projet.uf.template.application.ports.out;

import projet.uf.template.domain.model.Template;

import java.util.List;

public interface TemplatePersistencePort {
    List<Template> findByName(String name);
    List<Template> findByNameLike(String name);
    Template save(Template template);
}
