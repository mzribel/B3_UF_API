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
    @Builder.Default
    private boolean isActive = true;
    @Builder.Default
    @JsonProperty("isDerogatory")
    private boolean isDerogatory = false;

    public static final String affixRegex = "^[A-Za-zÀ-ÖØ-öø-ÿ@\\-' *]{1,30}$";

    public Breeder(String name, String siret, String affix, boolean isAffixPrefix, Long ownerId, Long addressId, Long createdByCatteryId, boolean isActive, boolean isDerogatory) {
        this.name = name != null ? name.trim() : null;
        this.siret = siret != null ? siret.trim() : null;
        this.affix = formatAffix(affix);
        this.isAffixPrefix = isAffixPrefix;
        this.ownerId = ownerId;
        this.addressId = addressId;
        this.createdByCatteryId = createdByCatteryId;
        this.isActive = isActive;
        this.isDerogatory = isDerogatory;
    }

    public Breeder(String name, Long createdByCatteryId) {
        this.name = name.trim();
        this.createdByCatteryId = createdByCatteryId;
    }

    public boolean isValidAffix(String affix) {
        return affix.matches(affixRegex);
    }
    private String formatAffix(String affix) {
        if (affix == null || affix.isEmpty()) { return null; }
        if (!this.isValidAffix(affix)) {
            throw new IllegalArgumentException("Format de l'affixe invalide");
        }
        return affix.trim().replaceAll("\\s{2,}", " ").toUpperCase();
    }
}
