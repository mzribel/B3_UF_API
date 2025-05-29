package projet.uf.modules.cat.application.ports.model;

import projet.uf.modules.cat.domain.model.Cat;

public class CatCommandMapper {
    public static Cat fromCreateCommand(CreateCatCommand command, Long createdByCatteryId) {
        return new Cat(
                command.name(),
                command.surname(),
                command.sex(),
                command.birthDate(),
                command.litterId(),
                command.originBreederId(),
                command.currentBreederId(),
                null,
                null,
                null,
                command.pedigreeNo(),
                command.identificationNo(),
                Boolean.TRUE.equals(command.isNeutered()),
                command.neuteredDate(),
                Boolean.TRUE.equals(command.isDeceased()),
                command.deceasedDate(),
                createdByCatteryId,
                Boolean.TRUE.equals(command.isInCattery()),
                command.notes()
        );
    }
}
