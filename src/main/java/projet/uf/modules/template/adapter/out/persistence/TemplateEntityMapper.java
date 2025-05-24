package projet.uf.modules.template.adapter.out.persistence;

import projet.uf.modules.template.domain.model.Template;

public class TemplateEntityMapper {
    public static Template toModel(TemplateEntity entity) {
        return new Template(entity.id, entity.name);
    }

    public static TemplateEntity toEntity(Template model) {
        TemplateEntity entity = new TemplateEntity();
        entity.setName(model.getName());
        return entity;
    }
}
