package projet.uf.modules.loof_characteristics.adapters.out.persistence.poly_types;

import org.springframework.stereotype.Component;
import projet.uf.modules.loof_characteristics.adapters.out.persistence.LoofCharacteristicEntityMapper;
import projet.uf.modules.loof_characteristics.adapters.out.persistence.coat_white_marking.CoatWhiteMarkingEntity;
import projet.uf.modules.loof_characteristics.domain.model.CoatWhiteMarking;
import projet.uf.modules.loof_characteristics.domain.model.PolyType;

@Component
public class PolyTypeEntityMapper implements LoofCharacteristicEntityMapper<PolyType, PolyTypeEntity> {

    @Override
    public PolyTypeEntity toEntity(PolyType model) {
        return new PolyTypeEntity(model.getCode(), model.getName(), model.getDetails());
    }

    @Override
    public PolyType toModel(PolyTypeEntity entity) {
        return new PolyType(entity.getId(), entity.getCode(), entity.getName(), entity.getDetails());
    }
}
