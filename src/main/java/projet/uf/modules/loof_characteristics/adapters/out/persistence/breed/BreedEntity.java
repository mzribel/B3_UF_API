package projet.uf.modules.loof_characteristics.adapters.out.persistence.breed;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import projet.uf.modules.loof_characteristics.adapters.out.persistence.LoofCharacteristicEntity;

@Entity
@Table(name="breeds")
@AllArgsConstructor
public class BreedEntity extends LoofCharacteristicEntity {
    public BreedEntity(String code, String name, String details) {
        super(code, name, details);
    }
}
