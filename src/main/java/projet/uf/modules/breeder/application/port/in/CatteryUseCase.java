package projet.uf.modules.breeder.application.port.in;

import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.command.AddUserToCatteryCommand;
import projet.uf.modules.breeder.application.dto.CatteryDetailsDto;
import projet.uf.modules.breeder.application.command.CreateCatteryCommand;
import projet.uf.modules.breeder.application.dto.UserCatteriesDto;

import java.util.List;

public interface CatteryUseCase {
    // CREATE
    CatteryDetailsDto create(CreateCatteryCommand command, OperatorUser operatorUser);

    // READ
    CatteryDetailsDto getById(Long id, OperatorUser operator);

    List<CatteryDetailsDto> getAll(OperatorUser operator);

    // DELETE
    void deleteById(Long id, OperatorUser operator);

    // ADD USER
    void addUserToCattery(Long catteryId, AddUserToCatteryCommand command, OperatorUser operator);
    // REMOVE USER
    void removeUserFromCattery(Long catteryId, Long userIdToRemove, OperatorUser operator);
    // PROMOTE TO ADMIN
    void promoteUserAdminOfCattery(Long userId, OperatorUser operator, Long catteryId);

    UserCatteriesDto getUserCatteries(Long userId, OperatorUser operator);

}
