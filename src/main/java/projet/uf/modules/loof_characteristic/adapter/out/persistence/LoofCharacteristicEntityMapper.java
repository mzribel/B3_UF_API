package projet.uf.modules.loof_characteristic.adapter.out.persistence;

import org.springframework.stereotype.Component;
import projet.uf.modules.loof_characteristic.domain.model.ALoofCharacteristic;

@Component
public interface LoofCharacteristicEntityMapper<M extends ALoofCharacteristic, E extends LoofCharacteristicEntity> {
    E toEntity(M model);
    M toModel(E entity);
}
