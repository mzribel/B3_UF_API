package projet.uf.modules.loof_characteristics.adapters.out.persistence.coat_pattern;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import projet.uf.modules.loof_characteristics.adapters.out.persistence.LoofCharacteristicEntity;

@Entity
@Table(name="coat_patterns")
@AllArgsConstructor
public class CoatPatternEntity extends LoofCharacteristicEntity {
    public CoatPatternEntity(String code, String name, String details) {
        super(code, name, details);
    }
}
