package projet.uf.modules.breeder.application.port.in;

import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.model.AddUserToCatteryCommand;
import projet.uf.modules.breeder.application.model.CatteryDetails;
import projet.uf.modules.breeder.application.model.CreateCatteryCommand;
import projet.uf.modules.breeder.application.model.UserCatteries;

import java.util.List;

public interface CatteryUseCase {
    // CREATE
    CatteryDetails create(CreateCatteryCommand command, OperatorUser operatorUser);

    // READ
    CatteryDetails getById(Long id, OperatorUser operator);

    List<CatteryDetails> getAll(OperatorUser operator);

    // DELETE
    void deleteById(Long id, OperatorUser operator);

    // ADD USER
    void addUserToCattery(Long catteryId, AddUserToCatteryCommand command, OperatorUser operator);
    // REMOVE USER
    void removeUserFromCattery(Long catteryId, Long userIdToRemove, OperatorUser operator);

    UserCatteries getUserCatteries(Long userId, OperatorUser operator);
}
