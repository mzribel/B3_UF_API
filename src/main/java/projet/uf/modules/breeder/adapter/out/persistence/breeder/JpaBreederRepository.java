package projet.uf.modules.breeder.adapter.out.persistence.breeder;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaBreederRepository extends JpaRepository<BreederEntity, Long> {
    List<BreederEntity> findByCreatedByCatteryId(Long createdByCatteryId);
    boolean existsByAffixAndCreatedByCatteryId(String affix, Long createdByCatteryId);
}
