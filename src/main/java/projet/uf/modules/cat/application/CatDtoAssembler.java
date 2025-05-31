package projet.uf.modules.cat.application;

import lombok.AllArgsConstructor;
import projet.uf.modules.breeder.application.dto.BreederSummaryDto;
import projet.uf.modules.breeder.application.port.out.BreederPersistencePort;
import projet.uf.modules.breeder.domain.model.Breeder;
import projet.uf.modules.cat.application.dto.CatCoatDto;
import projet.uf.modules.cat.application.dto.CatDetailsDto;
import projet.uf.modules.cat.application.dto.CatPedigreeDto;
import projet.uf.modules.cat.application.ports.in.CatCoatUseCase;
import projet.uf.modules.cat.application.ports.out.CatPersistencePort;
import projet.uf.modules.cat.application.ports.out.LitterPersistencePort;
import projet.uf.modules.cat.domain.model.Cat;
import projet.uf.modules.cat.domain.model.CatCoat;
import projet.uf.modules.cat.domain.model.Litter;

@AllArgsConstructor
public class CatDtoAssembler {
    private final BreederPersistencePort breederPersistencePort;
    private final CatCoatUseCase catCoatUseCase;
    private final LitterPersistencePort litterPersistencePort;
    private final CatPersistencePort catPersistencePort;

    public CatDetailsDto toDetailsDto(Cat cat) {
        if (cat == null) return null;

        Litter litter = (cat.getLitterId() != null) ? litterPersistencePort.getById(cat.getLitterId()).orElse(null) : null;
        Breeder originBreeder = (litter != null && litter.getOriginBreederId() != null)
                ? breederPersistencePort.getById(litter.getOriginBreederId()).orElse(null)
                : null;

        return CatDetailsDto.toDto(
                cat,
                getCatCoatDtoOrNull(cat.getId()),
                litter,
                originBreeder
        );
    }

    public CatPedigreeDto toPedigreeDto(Cat cat, int ascendanceLevel) {
        if (cat == null) return null;

        Litter litter = (cat.getLitterId() != null) ? litterPersistencePort.getById(cat.getLitterId()).orElse(null) : null;

        return CatPedigreeDto.toDto(
                cat,
                getCatCoatDtoOrNull(cat.getId()),
                litter != null ? litter.getBirthDate() : null,
                getBreederSummaryDto(litter),
                litter != null ? getCatPedigreeDtoOrNull(litter.getSireId(), ascendanceLevel-1) : null,
                litter != null ? getCatPedigreeDtoOrNull(litter.getDamId(), ascendanceLevel-1) : null
                );
    }

    private CatCoatDto getCatCoatDtoOrNull(Long catId) {
        CatCoat catCoat = catCoatUseCase.getCatCoatByCatId(catId);
        return catCoat != null
            ? catCoatUseCase.toDto(catCoat, null)
            : null;
    }

    private CatPedigreeDto getCatPedigreeDtoOrNull(Long catId, int ascendanceLevel) {
        if (catId == null || ascendanceLevel <= 0) return null;
        Cat cat = catPersistencePort.getById(catId).orElse(null);
        if (cat == null) return null;

        return toPedigreeDto(cat, ascendanceLevel-1);
    }

    private BreederSummaryDto getBreederSummaryDto(Litter litter) {
        if (litter == null || litter.getOriginBreederId() == null) return null;

        return breederPersistencePort.getById(litter.getOriginBreederId())
                .map(BreederSummaryDto::toDto)
                .orElse(null);
    }
}
