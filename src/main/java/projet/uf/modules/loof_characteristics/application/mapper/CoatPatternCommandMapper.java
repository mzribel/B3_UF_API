package projet.uf.modules.loof_characteristics.application.mapper;

import projet.uf.modules.loof_characteristics.application.ports.in.CreateLoofCharacteristicCommand;
import projet.uf.modules.loof_characteristics.domain.model.CoatPattern;

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
