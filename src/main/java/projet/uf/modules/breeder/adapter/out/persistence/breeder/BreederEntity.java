package projet.uf.modules.breeder.adapter.out.persistence.breeder;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projet.uf.modules.breeder.domain.model.Breeder;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="breeders")
public class BreederEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String siret;

    @Column(nullable = false)
    private String affix;

    @Column(nullable = false, name = "is_affix_prefix")
    private boolean isAffixPrefix = false;

    @Column
    private Long ownerId;

    @Column
    private Long addressId;

    @Column(nullable = false)
    private Long createdByCatteryId;

    @Column(nullable = false, name = "is_active")
    private boolean isActive = true;

    @Column(nullable = false, name = "is_derogatory")
    private boolean isDerogatory = false;

    public static Breeder toModel(BreederEntity entity) {
        return new Breeder(
                entity.getId(),
                entity.getName(),
                entity.getSiret(),
                entity.getAffix(),
                entity.isAffixPrefix(),
                entity.getOwnerId(),
                entity.getAddressId(),
                entity.getCreatedByCatteryId(),
                entity.isActive(),
                entity.isDerogatory()
        );
    }

    public static BreederEntity toEntity(Breeder model) {
        return new BreederEntity(
                model.getId(),
                model.getName(),
                model.getSiret(),
                model.getAffix(),
                model.isAffixPrefix(),
                model.getOwnerId(),
                model.getAddressId(),
                model.getCreatedByCatteryId(),
                model.isActive(),
                model.isDerogatory()
        );
    }
}
