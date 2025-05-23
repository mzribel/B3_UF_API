package projet.uf.modules.cat.application.ports.in;

import projet.uf.modules.cat.domain.model.Cat;

public interface UpdateCatUseCase {
    Cat updateCat(Long id, CreateCatCommand command) throws Exception;
}
