package projet.uf.modules.template.domain.model;

import projet.uf.modules.template.adapter.out.persistence.TemplateEntity;

public record Template(String name) {

    public static Template from(final TemplateEntity templateEntity) {
        return new Template(templateEntity.getName());
    }

    public String getName() {
        return name;
    }
}
