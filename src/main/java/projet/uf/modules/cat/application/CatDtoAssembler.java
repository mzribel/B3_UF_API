package projet.uf.modules.cat.application;

import lombok.AllArgsConstructor;
import projet.uf.modules.breeder.application.port.out.BreederPersistencePort;
import projet.uf.modules.breeder.domain.model.Breeder;
import projet.uf.modules.cat.application.dto.CatCoatDto;
import projet.uf.modules.cat.application.dto.CatDetailsDto;
import projet.uf.modules.cat.application.dto.CatLoofDto;
import projet.uf.modules.cat.application.ports.in.CatCoatUseCase;
import projet.uf.modules.cat.application.ports.out.CatPersistencePort;
import projet.uf.modules.cat.domain.model.Cat;
import projet.uf.modules.cat.domain.model.CatCoat;

import java.util.Optional;

@AllArgsConstructor
public class CatDtoAssembler {
    private final BreederPersistencePort breederPersistencePort;
    private final CatPersistencePort catPersistencePort;
    private final CatCoatUseCase catCoatUseCase;

    public CatDetailsDto toDetailsDto(Cat cat) {
        if (cat == null) return null;
        int ascendanceLevel = 1;

        CatLoofDto sire = (ascendanceLevel > 0)
                ? getCatLoofDtoOrNull(1L, ascendanceLevel - 1)
                : null;

        CatLoofDto dam = (ascendanceLevel > 0)
                ? getCatLoofDtoOrNull(2L, ascendanceLevel - 1)
                : null;

        // 📦 DTO final
        return CatDetailsDto.toDto(
                cat,
                dam,
                sire,
                getCatCoatDtoOrNull(cat.getId()),
                getBreederOrNull(cat.getOriginBreederId()),
                getBreederOrNull(cat.getCurrentBreederId())
        );
    }

    public CatLoofDto toLoofDto(Cat cat, int ascendanceLevel) {
        if (cat == null) return null;

        CatLoofDto sire = (ascendanceLevel > 0)
                ? getCatLoofDtoOrNull(1L, ascendanceLevel - 1)
                : null;

        CatLoofDto dam = (ascendanceLevel > 0)
                ? getCatLoofDtoOrNull(2L, ascendanceLevel - 1)
                : null;

        return CatLoofDto.toDto(
                cat,
                dam,
                sire,
                getCatCoatDtoOrNull(cat.getId()),
                getBreederOrNull(cat.getOriginBreederId())
        );
    }

    // Helperzzz
    private Breeder getBreederOrNull(Long breederId) {
        return Optional.ofNullable(breederId)
                .flatMap(breederPersistencePort::getById)
                .orElse(null);
    }
    private CatCoatDto getCatCoatDtoOrNull(Long catId) {
        CatCoat catCoat = catCoatUseCase.getCatCoatByCatId(catId);
        return catCoat != null
            ? catCoatUseCase.toDto(catCoat, null)
            : null;
    }
    private CatLoofDto getCatLoofDtoOrNull(Long catId, int ascendanceLevel) {
        return Optional.ofNullable(catId)
                .flatMap(catPersistencePort::getById)
                .map(father -> this.toLoofDto(father, ascendanceLevel - 1))
                .orElse(null);
    }
}
