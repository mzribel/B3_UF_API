package projet.uf.modules.breeder.adapter.out.persistence.cattery;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projet.uf.modules.breeder.domain.model.Cattery;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "catteries")
public class CatteryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "created_by_user_id")
    private Long createdByUserId;

    @Column(name = "linked_to_breeder_id")
    private Long linkedToBreederId;

    public static Cattery toModel(CatteryEntity entity) {
        return new Cattery(
                entity.getId(),
                entity.getCreatedByUserId(),
                entity.getLinkedToBreederId()
        );
    }

    public static CatteryEntity toEntity(Cattery model) {
        return new CatteryEntity(
                model.getId(),
                model.getCreatedByUserId(),
                model.getLinkedToBreederId()
        );
    }
}
