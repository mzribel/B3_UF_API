package projet.uf.modules.cat.application.dto;

import jakarta.validation.constraints.NotNull;
import projet.uf.modules.breeder.application.dto.BreederSummaryDto;
import projet.uf.modules.cat.domain.model.Cat;

import java.time.LocalDate;

public record CatPedigreeDto(
        Long id,
        String name,
        boolean isFemale,

        BreederSummaryDto breeder,
        CatCoatDto attributes,

        CatPedigreeDto dam,
        CatPedigreeDto sire,
        LocalDate birthDate,

        String pedigreeNumber,
        String identificationNumber
) {
    public static CatPedigreeDto toDto(@NotNull Cat cat,
                                       CatCoatDto coat,
                                       LocalDate birthDate,
                                       BreederSummaryDto originBreeder,
                                       CatPedigreeDto sire,
                                       CatPedigreeDto dam
                                   ) {

        return new CatPedigreeDto(
                cat.getId(),
                cat.getName(),
                cat.isSex(),

                originBreeder,

                coat,

                sire,
                dam,
                birthDate,

                cat.getPedigreeNo(),
                cat.getIdentificationNo()
        );
    }
}
