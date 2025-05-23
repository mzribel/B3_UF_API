package projet.uf.modules.loof_characteristics.application.mapper;

import projet.uf.modules.loof_characteristics.application.ports.in.CreateLoofCharacteristicCommand;
import projet.uf.modules.loof_characteristics.domain.model.CoatColor;

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
