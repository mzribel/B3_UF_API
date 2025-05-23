package projet.uf.modules.loof_characteristic.adapter.out.persistence.coat_white_marking;

import org.springframework.stereotype.Component;
import projet.uf.modules.loof_characteristic.adapter.out.persistence.LoofCharacteristicEntityMapper;
import projet.uf.modules.loof_characteristic.domain.model.CoatWhiteMarking;

@Component
public class CoatWhiteMarkingEntityMapper implements LoofCharacteristicEntityMapper<CoatWhiteMarking, CoatWhiteMarkingEntity> {

    @Override
    public CoatWhiteMarkingEntity toEntity(CoatWhiteMarking model) {
        return new CoatWhiteMarkingEntity(model.getCode(), model.getName(), model.getDetails());
    }

    @Override
    public CoatWhiteMarking toModel(CoatWhiteMarkingEntity entity) {
        return new CoatWhiteMarking(entity.getId(), entity.getCode(), entity.getName(), entity.getDetails());
    }
}
