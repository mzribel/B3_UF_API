package projet.uf.modules.breeder.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("isAffixPrefix")
    private boolean isAffixPrefix;
    private Long ownerId;
    private Long addressId;
    private Long createdByCatteryId;
    @JsonProperty("isActive")
    private boolean isActive = true;
    @JsonProperty("isDerogatory")
    private boolean isDerogatory = false;

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
    public Breeder(String name, Long createdByCatteryId) {
        this.name = name;
        this.createdByCatteryId = createdByCatteryId;
    }
}
