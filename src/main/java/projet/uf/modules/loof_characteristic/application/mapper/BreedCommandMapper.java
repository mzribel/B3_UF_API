package projet.uf.modules.loof_characteristic.application.mapper;

import projet.uf.modules.loof_characteristic.application.port.in.CreateLoofCharacteristicCommand;
import projet.uf.modules.loof_characteristic.domain.model.Breed;

public class BreedCommandMapper implements LoofCharacteristicCommandMapper<Breed> {

    @Override
    public Breed fromCreateCommand(CreateLoofCharacteristicCommand command) {
        return new Breed(
                command.code(),
                command.name(),
                command.details()
        );
    }
}
