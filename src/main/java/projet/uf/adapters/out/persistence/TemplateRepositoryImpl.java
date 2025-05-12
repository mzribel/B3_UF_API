package projet.uf.adapters.out.persistence;

import org.springframework.stereotype.Repository;
import projet.uf.domain.model.Template;
import projet.uf.application.ports.out.TemplateRepository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TemplateRepositoryImpl implements TemplateRepository {

    private final JpaTemplateRepository jpaRepository;

    public TemplateRepositoryImpl(JpaTemplateRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Template> findByName(String name) {
        return jpaRepository.findByName(name).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Template> findByNameLike(String name) {
        return jpaRepository.findByNameLike(name).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Template save(Template template) {
        TemplateEntity saved = jpaRepository.save(toEntity(template));
        return toDomain(saved);
    }



    private Template toDomain(TemplateEntity entity) {
        return new Template(entity.getName());
    }

    private TemplateEntity toEntity(Template template) {
        TemplateEntity entity = new TemplateEntity();
        entity.setName(template.getName());
        return entity;
    }
}