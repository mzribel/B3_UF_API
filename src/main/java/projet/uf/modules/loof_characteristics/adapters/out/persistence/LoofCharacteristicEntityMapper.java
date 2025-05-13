package projet.uf.modules.loof_characteristics.adapters.out.persistence;

import org.springframework.stereotype.Component;
import projet.uf.modules.loof_characteristics.domain.model.ALoofCharacteristic;

@Component
public interface LoofCharacteristicEntityMapper<M extends ALoofCharacteristic, E extends LoofCharacteristicEntity> {
    E toEntity(M model);
    M toModel(E entity);
}
