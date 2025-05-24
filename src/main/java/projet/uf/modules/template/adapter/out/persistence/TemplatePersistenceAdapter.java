package projet.uf.modules.template.adapter.out.persistence;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import projet.uf.modules.template.domain.model.Template;
import projet.uf.modules.template.application.port.out.TemplatePersistencePort;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class TemplatePersistenceAdapter implements TemplatePersistencePort {
    private final JpaTemplateRepository jpaRepository;

    @Override
    public List<Template> findByName(String name) {
        return jpaRepository.findByName(name).stream()
            .map(TemplateEntityMapper::toModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<Template> findByNameLike(String name) {
        return jpaRepository.findByNameLike(name).stream()
            .map(TemplateEntityMapper::toModel)
            .collect(Collectors.toList());
    }

    @Override
    public Template save(Template template) {
        TemplateEntity saved = jpaRepository.save(TemplateEntityMapper.toEntity(template));
        return TemplateEntityMapper.toModel(saved);
    }
}