package projet.uf.modules.loof_characteristics.application.mapper;

import projet.uf.modules.loof_characteristics.application.ports.in.CreateLoofCharacteristicCommand;
import projet.uf.modules.loof_characteristics.domain.model.CoatEffect;

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
