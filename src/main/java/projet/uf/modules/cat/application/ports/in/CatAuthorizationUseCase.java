package projet.uf.modules.cat.application.ports.in;

import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.cat.domain.model.Cat;

public interface CatAuthorizationUseCase {
    Cat getCatOrThrow(Long catId, OperatorUser operator);
    boolean hasUserAccessToCat(Long catId, OperatorUser operator);
}
