package projet.uf.modules.cat.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCatRepository extends JpaRepository<CatEntity, Long> {
    List<CatEntity> findAllByCreatedByCatteryId(Long id);
}
