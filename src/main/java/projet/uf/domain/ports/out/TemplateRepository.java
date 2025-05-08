package projet.uf.domain.ports.out;

import projet.uf.domain.model.Template;

import java.util.List;

public interface TemplateRepository {
    List<Template> findByName(String name);
    List<Template> findByNameLike(String name);
    Template save(Template template);
}
