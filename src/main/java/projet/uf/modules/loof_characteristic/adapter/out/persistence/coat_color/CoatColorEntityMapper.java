package projet.uf.modules.loof_characteristic.adapter.out.persistence.coat_color;

import org.springframework.stereotype.Component;
import projet.uf.modules.loof_characteristic.adapter.out.persistence.LoofCharacteristicEntityMapper;
import projet.uf.modules.loof_characteristic.domain.model.CoatColor;

@Component
public class CoatColorEntityMapper implements LoofCharacteristicEntityMapper<CoatColor, CoatColorEntity> {

    @Override
    public CoatColorEntity toEntity(CoatColor model) {
        return new CoatColorEntity(model.getCode(), model.getName(), model.getDetails());
    }

    @Override
    public CoatColor toModel(CoatColorEntity entity) {
        return new CoatColor(entity.getId(), entity.getCode(), entity.getName(), entity.getDetails());
    }
}
