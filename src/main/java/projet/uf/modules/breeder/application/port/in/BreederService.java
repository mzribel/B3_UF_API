package projet.uf.modules.breeder.application.port.in;

import projet.uf.modules.breeder.domain.model.Breeder;

import java.util.List;
import java.util.Optional;

public interface BreederService {
    // CREATE
    Breeder create(CreateBreederCommand command, Long createdByCatteryId);

    // READ
    Optional<Breeder> getById(Long id);
    List<Breeder> getAll();
    List<Breeder> getByCatteryId(Long id);

    // UPDATE
    Breeder update(Long id, CreateBreederCommand command);

    // DELETE
    void deleteById(Long id);
}
