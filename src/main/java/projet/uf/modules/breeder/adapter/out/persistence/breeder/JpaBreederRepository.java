package projet.uf.modules.breeder.adapter.out.persistence.breeder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaBreederRepository extends JpaRepository<BreederEntity, Long> {
    boolean existsByAffixAndCreatedByCatteryId(String affix, Long createdByCatteryId);
    boolean existsByAffixAndCreatedByCatteryIdAndIdNot(String affix, Long createdByCatteryId, Long id);

    @Query(value = """
    SELECT * FROM breeders b
    WHERE b.created_by_cattery_id = :catteryId
    AND b.id NOT IN (
        SELECT c.linked_to_breeder_id FROM catteries c
        WHERE c.linked_to_breeder_id IS NOT NULL
    )
    """, nativeQuery = true)
    List<BreederEntity> findAllContactsCreatedByCatteryId(@Param("catteryId") Long catteryId);

    @Query(value = """
        SELECT b.* FROM breeders as b
        RIGHT JOIN catteries AS c on c.linked_to_breeder_id = b.id
        WHERE c.id = :catteryId;
    """, nativeQuery = true)
    Optional<BreederEntity> findBreederLinkedToCatteryId(@Param("catteryId") Long catteryId);
}
