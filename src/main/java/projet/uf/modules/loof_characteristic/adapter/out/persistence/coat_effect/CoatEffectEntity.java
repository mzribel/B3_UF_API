package projet.uf.modules.loof_characteristic.adapter.out.persistence.coat_effect;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import projet.uf.modules.loof_characteristic.adapter.out.persistence.LoofCharacteristicEntity;

@Entity
@Table(name="coat_effects")
@AllArgsConstructor
public class CoatEffectEntity extends LoofCharacteristicEntity {
    public CoatEffectEntity(String code, String name, String details) {
        super(code, name, details);
    }
}
