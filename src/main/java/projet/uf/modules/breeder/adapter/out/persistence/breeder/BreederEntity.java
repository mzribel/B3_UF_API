package projet.uf.modules.breeder.adapter.out.persistence.breeder;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false)
    private boolean isAffixPrefix = false;

    @Column
    private Long ownerId;

    @Column
    private Long addressId;

    @Column(nullable = false)
    private Long createdByCatteryId;

    @Column(nullable = false)
    private boolean isActive = true;

    @Column(nullable = false)
    private boolean isDerogatory = false;

    public BreederEntity(String name, String siret, String affix, boolean isAffixPrefix, Long createdByCatteryId, boolean isActive, boolean isDerogatory) {
        this.name = name;
        this.siret = siret;
        this.affix = affix;
        this.isAffixPrefix = isAffixPrefix;
        this.createdByCatteryId = createdByCatteryId;
        this.isActive = isActive;
        this.isDerogatory = isDerogatory;
    }
}
