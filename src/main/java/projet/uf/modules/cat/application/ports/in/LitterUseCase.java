package projet.uf.modules.cat.application.ports.in;

import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.cat.application.command.LitterCommand;
import projet.uf.modules.cat.domain.model.Litter;

import java.util.List;

public interface LitterUseCase {
    List<Litter> getAll(OperatorUser operator);
    Litter getById(Long litterId, OperatorUser operator);

    List<Litter> getByCatteryId(Long litterId, OperatorUser operator);

    Litter updateLitterById(Long litterId, LitterCommand command, OperatorUser operator);
    void deleteLitterById(Long litterId, OperatorUser operator);

    Litter getCatLitter(Long catId, OperatorUser operator);
}
