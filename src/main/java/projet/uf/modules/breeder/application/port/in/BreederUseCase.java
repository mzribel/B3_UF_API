package projet.uf.modules.breeder.application.port.in;

import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.command.CreateContactBreederCommand;
import projet.uf.modules.breeder.application.command.UpdateCatteryBreederCommand;
import projet.uf.modules.breeder.domain.model.Breeder;

import java.util.List;

public interface BreederUseCase {
    // TOUS SANS DISTINCTION
    Breeder getById(Long id, OperatorUser operator);
    List<Breeder> getAll(OperatorUser operator);
    void deleteById(Long catteryId, OperatorUser operator);

    // CATTERY BREEDER
    Breeder getCatteryBreederByCatteryId(Long catteryId, OperatorUser operator);
    Breeder createEmptyCatteryBreeder(String name, Long createdByCatteryId);
    Breeder updateCatteryBreeder(Long catteryId, UpdateCatteryBreederCommand command, OperatorUser operator);

    // CONTACT BREEDERS
    Breeder createContactBreeder(CreateContactBreederCommand command, Long createdByCatteryId, OperatorUser operator);
    List<Breeder> getAllContactBreedersByCatteryId(Long catteryId, OperatorUser operator);
    Breeder updateContactBreeder(Long breederId, Long catteryId, CreateContactBreederCommand command, OperatorUser operator);
    void deleteContactBreeder(Long breederId, Long catteryId, OperatorUser operator);
}
