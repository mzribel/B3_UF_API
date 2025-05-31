package projet.uf.modules.cat.application.ports.in;

import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.cat.application.dto.CatDetailsDto;
import projet.uf.modules.cat.application.command.CatCommand;
import projet.uf.modules.cat.application.dto.CatPedigreeDto;

import java.util.List;

public interface CatUseCase {
    List<CatDetailsDto> getAll(OperatorUser operator);
    CatDetailsDto getById(Long id, OperatorUser operator);

    List<CatDetailsDto> getByCatteryId(Long id, OperatorUser operator);
    CatDetailsDto createCat(CatCommand command, Long createdByCatteryId, OperatorUser operator);

    CatDetailsDto updateCatById(Long id, CatCommand command, OperatorUser operator);
    void deleteCatById(Long id, OperatorUser operator);

    CatPedigreeDto getPedigreeById(Long id, OperatorUser operator);
}
