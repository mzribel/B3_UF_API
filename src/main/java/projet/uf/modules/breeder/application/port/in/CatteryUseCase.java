package projet.uf.modules.breeder.application.port.in;

import projet.uf.modules.breeder.application.model.CatteryDetails;
import projet.uf.modules.breeder.domain.model.Cattery;

import java.util.List;

public interface CatteryUseCase {
    // CREATE
    Cattery create(Long createdByUserId);

    // READ
    Cattery getById(Long id);
    CatteryDetails getCatteryDetailsById(Long id);
    List<CatteryDetails> getAllAccessibleByUser(Long userId);
    CatteryDetails getByIdIfAuthorized(Long catteryId, Long userId);
    List<Cattery> getAll();
    List<CatteryDetails> getAllCatteryDetails();
}
