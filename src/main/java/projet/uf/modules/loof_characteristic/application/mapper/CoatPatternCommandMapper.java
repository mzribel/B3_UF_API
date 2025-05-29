package projet.uf.modules.loof_characteristic.application.mapper;

import projet.uf.modules.loof_characteristic.application.port.in.CreateLoofCharacteristicCommand;
import projet.uf.modules.loof_characteristic.domain.model.CoatPattern;

public class CoatPatternCommandMapper implements LoofCharacteristicCommandMapper<CoatPattern> {

    @Override
    public CoatPattern fromCreateCommand(CreateLoofCharacteristicCommand command) {
        return new CoatPattern(
                command.code(),
                command.name(),
                command.details()
        );
    }
}
