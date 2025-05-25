package projet.uf.modules.breeder.application.port.in;

import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.model.CatteryDetails;
import projet.uf.modules.breeder.domain.model.Cattery;

import java.util.List;

public interface CatteryUseCase {
    // CREATE
    Cattery create(Long createdByUserId, OperatorUser operator);

    // READ
    CatteryDetails getById(Long id, OperatorUser operator);

    List<CatteryDetails> getAll(OperatorUser operator);

    // DELETE
    void deleteById(Long id, OperatorUser operator);

    // ADD USER
    void addUserToCattery(Long catteryId, Long userId, String email, OperatorUser operator);
    // REMOVE USER
    void removeUserFromCattery(Long catteryId, Long userIdToRemove, OperatorUser operator);
}
