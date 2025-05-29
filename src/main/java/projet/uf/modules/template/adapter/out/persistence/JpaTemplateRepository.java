package projet.uf.modules.template.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaTemplateRepository extends JpaRepository<TemplateEntity, Long> {
    List<TemplateEntity> findByName(String name);

    @Query("select template from TemplateEntity template where template.id in (select max(template.id) from TemplateEntity template where template.name like %?1% group by template.name)")
    List<TemplateEntity> findByNameLike(String name);
}
