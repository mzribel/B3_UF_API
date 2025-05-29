package projet.uf.modules.loof_characteristic.adapter.out.persistence.poly_type;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import projet.uf.modules.loof_characteristic.adapter.out.persistence.LoofCharacteristicEntity;

@Entity
@Table(name="poly_types")
@AllArgsConstructor
public class PolyTypeEntity extends LoofCharacteristicEntity {
    public PolyTypeEntity(String code, String name, String details) {
        super(code, name, details);
    }
}
