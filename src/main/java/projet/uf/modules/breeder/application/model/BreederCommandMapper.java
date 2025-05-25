package projet.uf.modules.breeder.application.model;

import projet.uf.modules.breeder.domain.model.Breeder;

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
