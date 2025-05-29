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
}
