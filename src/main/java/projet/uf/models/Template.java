package projet.uf.models;

import projet.uf.entities.TemplateEntity;

public record Template(String name) {

    public static Template from(final TemplateEntity templateEntity) {
        return new Template(templateEntity.getName());
    }

}
