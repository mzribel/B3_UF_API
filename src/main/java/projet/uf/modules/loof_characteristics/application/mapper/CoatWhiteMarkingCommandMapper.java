package projet.uf.modules.loof_characteristics.application.mapper;

import projet.uf.modules.loof_characteristics.application.ports.in.CreateLoofCharacteristicCommand;
import projet.uf.modules.loof_characteristics.domain.model.CoatWhiteMarking;

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
