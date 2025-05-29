package projet.uf.modules.loof_characteristic.application.mapper;

import projet.uf.modules.loof_characteristic.application.port.in.CreateLoofCharacteristicCommand;
import projet.uf.modules.loof_characteristic.domain.model.CoatEffect;

public class CoatEffectCommandMapper implements LoofCharacteristicCommandMapper<CoatEffect> {

    @Override
    public CoatEffect fromCreateCommand(CreateLoofCharacteristicCommand command) {
        return new CoatEffect(
                command.code(),
                command.name(),
                command.details()
        );
    }
}
