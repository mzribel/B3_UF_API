package projet.uf.modules.loof_characteristic.application.mapper;

import projet.uf.modules.loof_characteristic.application.port.in.CreateLoofCharacteristicCommand;
import projet.uf.modules.loof_characteristic.domain.model.CoatWhiteMarking;

public class CoatWhiteMarkingCommandMapper implements LoofCharacteristicCommandMapper<CoatWhiteMarking> {

    @Override
    public CoatWhiteMarking fromCreateCommand(CreateLoofCharacteristicCommand command) {
        return new CoatWhiteMarking(
                command.code(),
                command.name(),
                command.details()
        );
    }
}
