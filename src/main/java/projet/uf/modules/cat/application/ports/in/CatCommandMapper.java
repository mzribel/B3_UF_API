package projet.uf.modules.cat.application.ports.in;

import projet.uf.modules.cat.domain.model.Cat;

public class CatCommandMapper {
    public static Cat fromCreateCommand(CreateCatCommand command, Long createdByCatteryId) {
        return new Cat(
                command.getName(),
                command.getSurname(),
                command.isSex(),
                command.getBirthDate(),
                command.getLitterId(),
                command.getOriginBreederId(),
                command.getCurrentBreederId(),
                null,
                null,
                null,
                command.getPedigreeNo(),
                command.getIdentificationNo(),
                Boolean.TRUE.equals(command.getIsNeutered()),
                command.getNeuteredDate(),
                Boolean.TRUE.equals(command.getIsDeceased()),
                command.getDeceasedDate(),
                createdByCatteryId,
                Boolean.TRUE.equals(command.getIsInCattery()),
                command.getNotes()
        );
    }
}
