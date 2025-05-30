package projet.uf.modules.cat.application.ports.out;

import projet.uf.modules.cat.domain.model.Litter;
import java.util.List;
import java.util.Optional;

public interface LitterPersistencePort {
    Optional<Litter> getById(Long litterId);
    List<Litter> getByCatteryId(Long litterId);
    List<Litter> getAll();
    Litter save(Litter litter);
    void deleteById(Long litterId);
}
