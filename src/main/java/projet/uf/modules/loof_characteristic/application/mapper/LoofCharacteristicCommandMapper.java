package projet.uf.modules.loof_characteristic.application.mapper;

import projet.uf.modules.loof_characteristic.application.port.in.CreateLoofCharacteristicCommand;
import projet.uf.modules.loof_characteristic.domain.model.ALoofCharacteristic;

public interface LoofCharacteristicCommandMapper<T extends ALoofCharacteristic> {
    public T fromCreateCommand(CreateLoofCharacteristicCommand command);
}
