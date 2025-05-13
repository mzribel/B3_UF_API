package projet.uf.modules.loof_characteristics.adapters.out.persistence.coat_effect;

import org.springframework.stereotype.Component;
import projet.uf.modules.loof_characteristics.adapters.out.persistence.LoofCharacteristicEntityMapper;
import projet.uf.modules.loof_characteristics.adapters.out.persistence.coat_color.CoatColorEntity;
import projet.uf.modules.loof_characteristics.domain.model.CoatColor;
import projet.uf.modules.loof_characteristics.domain.model.CoatEffect;

@Component
public class CoatEffectEntityMapper implements LoofCharacteristicEntityMapper<CoatEffect, CoatEffectEntity> {

    @Override
    public CoatEffectEntity toEntity(CoatEffect model) {
        return new CoatEffectEntity(model.getCode(), model.getName(), model.getDetails());
    }

    @Override
    public CoatEffect toModel(CoatEffectEntity entity) {
        return new CoatEffect(entity.getId(), entity.getCode(), entity.getName(), entity.getDetails());
    }
}
