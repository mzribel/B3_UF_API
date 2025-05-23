package projet.uf.modules.loof_characteristics.application.mapper;

import projet.uf.modules.loof_characteristics.application.ports.in.CreateLoofCharacteristicCommand;
import projet.uf.modules.loof_characteristics.domain.model.Breed;

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
