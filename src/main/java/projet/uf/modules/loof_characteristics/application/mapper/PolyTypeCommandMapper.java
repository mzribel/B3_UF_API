package projet.uf.modules.loof_characteristics.application.mapper;

import projet.uf.modules.loof_characteristics.application.ports.in.CreateLoofCharacteristicCommand;
import projet.uf.modules.loof_characteristics.domain.model.PolyType;

public class PolyTypeCommandMapper implements LoofCharacteristicCommandMapper<PolyType> {

    @Override
    public PolyType fromCreateCommand(CreateLoofCharacteristicCommand command) {
        return new PolyType(
                command.code(),
                command.name(),
                command.details()
        );
    }
}
