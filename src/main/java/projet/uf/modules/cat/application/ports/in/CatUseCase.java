package projet.uf.modules.cat.application.ports.in;

import projet.uf.modules.cat.domain.model.Cat;

import java.util.List;
import java.util.Optional;

public interface CatUseCase {
    Cat createCat(CreateCatCommand command);

    Optional<Cat> getById(Long id);
    List<Cat> getByCatteryId(Long id);
    List<Cat> getAll();

    Cat updateCat(Long id, CreateCatCommand command) throws Exception;
}
