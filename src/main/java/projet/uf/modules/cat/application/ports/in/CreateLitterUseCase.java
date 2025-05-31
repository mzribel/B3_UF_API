package projet.uf.modules.cat.application.ports.in;

import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.cat.application.command.LitterCommand;
import projet.uf.modules.cat.domain.model.Litter;

public interface CreateLitterUseCase {
    Litter createLitter(LitterCommand command, Long createdByCatteryId, OperatorUser operator);
}
