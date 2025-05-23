package projet.uf.modules.loof_characteristic.application.mapper;

import projet.uf.modules.loof_characteristic.application.port.in.CreateLoofCharacteristicCommand;
import projet.uf.modules.loof_characteristic.domain.model.CoatColor;

public class CoatColorCommandMapper implements LoofCharacteristicCommandMapper<CoatColor> {

    @Override
    public CoatColor fromCreateCommand(CreateLoofCharacteristicCommand command) {
        return new CoatColor(
                command.code(),
                command.name(),
                command.details()
        );
    }
}
