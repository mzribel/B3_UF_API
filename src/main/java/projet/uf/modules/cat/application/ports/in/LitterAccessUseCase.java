package projet.uf.modules.cat.application.ports.in;

import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.cat.domain.model.Litter;

public interface LitterAccessUseCase {
    Litter getLitterOrThrow(Long litterId, OperatorUser operator);
    boolean hasUserAccessToLitter(Long litterId, OperatorUser operator);
}
