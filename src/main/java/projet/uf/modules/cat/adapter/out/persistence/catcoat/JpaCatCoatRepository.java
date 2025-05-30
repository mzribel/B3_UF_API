package projet.uf.modules.cat.adapter.out.persistence.catcoat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaCatCoatRepository extends JpaRepository<CatCoatEntity, Long> {
    Optional<CatCoatEntity> findByCatId(Long id);
}
