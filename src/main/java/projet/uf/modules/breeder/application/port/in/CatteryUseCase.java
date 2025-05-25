package projet.uf.modules.breeder.application.port.in;

import projet.uf.modules.breeder.application.model.CatteryDetails;
import projet.uf.modules.breeder.domain.model.Cattery;

import java.util.List;

public interface CatteryUseCase {
    // CREATE
    Cattery create(Long createdByUserId);

    // READ
    CatteryDetails getById(Long id);
    CatteryDetails getByIdIfAuthorized(Long catteryId, Long userId);

    List<CatteryDetails> getAll();
    List<CatteryDetails> getAllAccessibleFromUser(Long userId);

    // DELETE
    void deleteById(Long id);
    void deleteByIdIfAuthorized(Long id, Long userId);
}
