package projet.uf.modules.loof_characteristics.adapters.out.persistence.poly_types;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import projet.uf.modules.loof_characteristics.adapters.out.persistence.LoofCharacteristicEntity;

@Entity
@Table(name="poly_types")
@AllArgsConstructor
public class PolyTypeEntity extends LoofCharacteristicEntity {
    public PolyTypeEntity(String code, String name, String details) {
        super(code, name, details);
    }
}
