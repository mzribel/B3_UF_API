package projet.uf.modules.loof_characteristics.adapters.out.persistence.breed;

import org.springframework.stereotype.Component;
import projet.uf.modules.loof_characteristics.adapters.out.persistence.LoofCharacteristicEntityMapper;
import projet.uf.modules.loof_characteristics.domain.model.Breed;

@Component
public class BreedEntityMapper implements LoofCharacteristicEntityMapper<Breed, BreedEntity> {
    @Override
    public BreedEntity toEntity(Breed model) {
        return new BreedEntity(model.getCode(), model.getName(), model.getDetails());
    }

    @Override
    public Breed toModel(BreedEntity entity) {
        return new Breed(entity.getId(), entity.getCode(), entity.getName(), entity.getDetails());
    }
}
