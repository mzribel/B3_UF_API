package projet.uf.modules.cat.application.ports.in;

import projet.uf.modules.cat.domain.model.Cat;

public interface CreateCatUseCase {
    Cat createCat(CreateCatCommand command);

}
