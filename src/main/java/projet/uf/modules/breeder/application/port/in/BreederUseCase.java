package projet.uf.modules.breeder.application.port.in;

import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.model.CreateContactBreederCommand;
import projet.uf.modules.breeder.application.model.UpdateCatteryBreederCommand;
import projet.uf.modules.breeder.domain.model.Breeder;

import java.util.List;

public interface BreederUseCase {
    // CREATE
    Breeder createContact(CreateContactBreederCommand command, Long createdByCatteryId, OperatorUser operator);
    Breeder createEmptyCatteryBreeder(String name, Long createdByCatteryId);


    Breeder updateContactBreeder(Long breederId, Long catteryId, CreateContactBreederCommand command, OperatorUser operator);
    Breeder updateCatteryBreeder(Long catteryId, UpdateCatteryBreederCommand command, OperatorUser operator);


        // READ
    Breeder getById(Long id, OperatorUser operator);
    List<Breeder> getAll(OperatorUser operator);
    List<Breeder> getByCatteryId(Long id);

    // UPDATE
    Breeder update(Long id, CreateContactBreederCommand command);

    // DELETE
    void deleteById(Long id);
}
