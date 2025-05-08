package projet.uf.domain.model;

import projet.uf.adapters.out.persistence.TemplateEntity;

public record Template(String name) {

    public static Template from(final TemplateEntity templateEntity) {
        return new Template(templateEntity.getName());
    }

    public String getName() {
        return name;
    }
}
