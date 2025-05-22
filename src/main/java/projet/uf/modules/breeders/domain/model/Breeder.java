package projet.uf.modules.breeders.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class Breeder {
    private Long id;
    private String name;
    private String siret;
    private String affix;
    private boolean isAffixPrefix;
    private Long ownerId;
    private Long addressId;
    private Long createdByCatteryId;
    private boolean isActive;
    private boolean isDerogatory;

    public Breeder(String name, String siret, String affix, boolean isAffixPrefix, Long ownerId, Long addressId, Long createdByCatteryId, boolean isActive, boolean isDerogatory) {
        this.name = name;
        this.siret = siret;
        this.affix = affix;
        this.isAffixPrefix = isAffixPrefix;
        this.ownerId = ownerId;
        this.addressId = addressId;
        this.createdByCatteryId = createdByCatteryId;
        this.isActive = isActive;
        this.isDerogatory = isDerogatory;
    }
}
