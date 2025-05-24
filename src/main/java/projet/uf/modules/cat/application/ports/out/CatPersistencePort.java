package projet.uf.modules.cat.application.ports.out;

import projet.uf.modules.cat.domain.model.Cat;

import java.util.List;
import java.util.Optional;

public interface CatPersistencePort {
    Optional<Cat> getById(Long id);
    List<Cat> getByCatteryId(Long id);
    List<Cat> getAll();
    Cat save(Cat cat);
    void deleteById(Long id);
}
