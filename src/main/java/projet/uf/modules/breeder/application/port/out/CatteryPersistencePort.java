package projet.uf.modules.breeder.application.port.out;

import projet.uf.modules.breeder.domain.model.Cattery;

import java.util.List;
import java.util.Optional;

public interface CatteryPersistencePort {
    Optional<Cattery> getById(Long id);
    List<Cattery> getByCreatedByUserId(Long id);
    List<Cattery> getAll();
    Cattery insert(Cattery cattery);
    Cattery update(Cattery cattery);
    void deleteById(Long id);

    boolean isBreederLinkedToAnyCattery(Long breederId);
}
