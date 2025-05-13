package projet.uf.template.domain.model;

import projet.uf.template.adapters.out.persistence.TemplateEntity;

public record Template(String name) {

    public static Template from(final TemplateEntity templateEntity) {
        return new Template(templateEntity.getName());
    }

    public String getName() {
        return name;
    }
}
