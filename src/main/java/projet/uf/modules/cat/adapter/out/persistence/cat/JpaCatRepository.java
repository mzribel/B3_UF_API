package projet.uf.modules.cat.adapter.out.persistence.cat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface JpaCatRepository extends JpaRepository<CatEntity, Long> {
    List<CatEntity> findAllByCreatedByCatteryId(Long id);
    List<CatEntity> findAllByLitterIdAndCreatedByCatteryId(Long litterId, Long catteryId);

    // Chats stérilisés dans une période donnée (bornes incluses) pour une chatterie
    @Query(
            value = """
            SELECT *
            FROM cats c
            WHERE c.created_by_cattery_id = :catteryId
              AND c.neutered = true
              AND c.neutered_date BETWEEN :start AND :end
            ORDER BY c.neutered_date DESC
            """,
            nativeQuery = true
    )
    List<CatEntity> findNeuteredBetweenByCattery(
            @Param("catteryId") Long catteryId,
            @Param("start") LocalDate startInclusive,
            @Param("end") LocalDate endInclusive
    );

    // Projection pour compter par race
    interface BreedCountProjection {
        Long getBreedId();
        Long getCnt();
    }

    // Statistiques: nombre de chats par race pour une chatterie
    @Query(
            value = """
            SELECT c.breed_id AS breedId, COUNT(*) AS cnt
            FROM cats c
            WHERE c.created_by_cattery_id = :catteryId
            GROUP BY c.breed_id
            HAVING COUNT(*) > 0
            ORDER BY cnt DESC
            """,
            nativeQuery = true
    )
    List<BreedCountProjection> countByBreedForCattery(@Param("catteryId") Long catteryId);
}
