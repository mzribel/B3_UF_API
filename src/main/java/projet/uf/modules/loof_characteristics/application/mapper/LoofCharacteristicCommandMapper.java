package projet.uf.modules.loof_characteristics.application.mapper;

import projet.uf.modules.loof_characteristics.application.ports.in.CreateLoofCharacteristicCommand;
import projet.uf.modules.loof_characteristics.domain.model.ALoofCharacteristic;

public interface LoofCharacteristicCommandMapper<T extends ALoofCharacteristic> {
    public T fromCreateCommand(CreateLoofCharacteristicCommand command);
}
