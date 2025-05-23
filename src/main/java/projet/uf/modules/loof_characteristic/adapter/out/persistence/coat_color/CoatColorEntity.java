package projet.uf.modules.loof_characteristic.adapter.out.persistence.coat_color;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import projet.uf.modules.loof_characteristic.adapter.out.persistence.LoofCharacteristicEntity;

@Entity
@Table(name="coat_colors")
@AllArgsConstructor
public class CoatColorEntity extends LoofCharacteristicEntity {
    public CoatColorEntity(String code, String name, String details) {
        super(code, name, details);
    }
}
