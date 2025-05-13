package projet.uf.modules.loof_characteristics.adapters.out.persistence.coat_pattern;

import org.springframework.stereotype.Component;
import projet.uf.modules.loof_characteristics.adapters.out.persistence.LoofCharacteristicEntityMapper;
import projet.uf.modules.loof_characteristics.domain.model.CoatPattern;

@Component
public class CoatPatternEntityMapper implements LoofCharacteristicEntityMapper<CoatPattern, CoatPatternEntity> {

    @Override
    public CoatPatternEntity toEntity(CoatPattern model) {
        return new CoatPatternEntity(model.getCode(), model.getName(), model.getDetails());
    }

    @Override
    public CoatPattern toModel(CoatPatternEntity entity) {
        return new CoatPattern(entity.getId(), entity.getCode(), entity.getName(), entity.getDetails());
    }
}
