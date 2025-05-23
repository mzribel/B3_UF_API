package projet.uf.modules.loof_characteristic.adapter.out.persistence.coat_white_marking;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import projet.uf.modules.loof_characteristic.adapter.out.persistence.LoofCharacteristicEntity;

@Entity
@Table(name="coat_white_markings")
@AllArgsConstructor
public class CoatWhiteMarkingEntity extends LoofCharacteristicEntity {
    public CoatWhiteMarkingEntity(String code, String name, String details) {
        super(code, name, details);
    }
}
