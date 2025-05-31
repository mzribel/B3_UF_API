package projet.uf.modules.cat.application.ports.in;

import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.cat.application.command.LitterCommand;
import projet.uf.modules.cat.application.dto.CatDetailsDto;
import projet.uf.modules.cat.application.command.CatCommand;
import projet.uf.modules.cat.application.dto.CatPedigreeDto;
import projet.uf.modules.cat.domain.model.Litter;

import java.util.List;

public interface CatUseCase {
    List<CatDetailsDto> getAll(OperatorUser operator);
    CatDetailsDto getById(Long id, OperatorUser operator);

    List<CatDetailsDto> getByCatteryId(Long id, OperatorUser operator);
    CatDetailsDto createCat(CatCommand command, Long createdByCatteryId, OperatorUser operator);

    CatDetailsDto updateCatById(Long id, CatCommand command, OperatorUser operator);
    void deleteCatById(Long id, OperatorUser operator);

    CatPedigreeDto getPedigreeById(Long id, OperatorUser operator);

    List<CatDetailsDto> getCatsByLitterId(Long litterId, OperatorUser operator);

    Litter updateCatLitter(Long catId, LitterCommand command, OperatorUser operator);

    void addKittenToLitter(Long catId, Long litterId, OperatorUser operator);
    void removeKittenFromLitter(Long catId, Long litterId, OperatorUser operator);
}
