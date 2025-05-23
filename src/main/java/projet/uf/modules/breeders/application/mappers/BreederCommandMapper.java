package projet.uf.modules.breeders.application.mappers;

import projet.uf.modules.breeders.application.ports.in.CreateBreederCommand;
import projet.uf.modules.breeders.domain.model.Breeder;

public class BreederCommandMapper {
    public static Breeder fromCreateCommand(CreateBreederCommand command, Long createdByCatteryId) {
        return new Breeder(
                command.getName(),
                command.getSiret(),
                command.getAffix(),
                command.isAffixPrefix(),
                null,
                null,
                createdByCatteryId,
                command.isActive(),
                command.isDerogatory()
        );
    }
}
