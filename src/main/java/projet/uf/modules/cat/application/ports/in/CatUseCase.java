package projet.uf.modules.cat.application.ports.in;

import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.cat.application.ports.dto.CatDetailsDto;
import projet.uf.modules.cat.application.ports.model.CreateCatCommand;
import projet.uf.modules.cat.domain.model.Cat;

import java.util.List;

public interface CatUseCase {
    List<Cat> getAll(OperatorUser operator);
    CatDetailsDto getById(Long id, OperatorUser operator);

    List<Cat> getByCatteryId(Long id, OperatorUser operator);
    CatDetailsDto createCat(CreateCatCommand command, Long createdByCatteryId, OperatorUser operator);

    Cat updateCatById(Long id, CreateCatCommand command, OperatorUser operator);
    void deleteCatById(Long id, OperatorUser operator);
}
