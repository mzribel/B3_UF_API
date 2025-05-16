package projet.uf.modules.cat.application.ports.in;

import projet.uf.modules.cat.domain.model.Cat;

import java.util.List;
import java.util.Optional;

public interface GetCatUseCase {
    Optional<Cat> getById(Long id);
    List<Cat> getByCatteryId(Long id);
    List<Cat> getAll();
}
