package projet.uf.modules.cat.application.ports.in;

import jakarta.validation.constraints.NotNull;
import projet.uf.modules.cat.application.ports.dto.CatCoatDto;
import projet.uf.modules.cat.application.ports.model.CatCoatCommand;
import projet.uf.modules.cat.domain.model.CatCoat;
import projet.uf.modules.loof_characteristic.domain.model.Breed;

public interface CatCoatUseCase {
    CatCoat getCatCoatByCatId(Long id);
    CatCoat createOrUpdateCatCoat(Long catId, CatCoatCommand command);
    CatCoatDto toDto(@NotNull CatCoat catCoat, Breed breed);
}
