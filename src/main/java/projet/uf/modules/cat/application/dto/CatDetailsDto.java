package projet.uf.modules.cat.application.dto;

import projet.uf.modules.breeder.application.dto.BreederSummaryDto;
import projet.uf.modules.breeder.domain.model.Breeder;
import projet.uf.modules.cat.domain.model.Cat;
import projet.uf.modules.cat.domain.model.Litter;

import java.time.LocalDate;

public record CatDetailsDto(
        Long id,
        String name,
        String surname,
        boolean isFemale,
        CatCoatDto coat,

        LitterSummaryDto litter,

        String pedigreeNumber,
        String identificationNumber,

        boolean isDeceased,
        LocalDate deceasedDate,
        boolean isNeutered,
        LocalDate neuteredDate,

        String notes,

        Long createdByCatteryId
) {
    public static CatDetailsDto toDto(Cat cat,
                                      CatCoatDto coat,
                                      Litter litter,
                                      Breeder originBreeder
                                      ) {
        return new CatDetailsDto(
                cat.getId(),
                cat.getName(),
                cat.getSurname(),
                cat.isSex(),
                coat,

                LitterSummaryDto.toDto(litter, BreederSummaryDto.toDto(originBreeder)),

                cat.getPedigreeNo(),
                cat.getIdentificationNo(),

                cat.isDeceased(),
                cat.getDeceasedDate(),
                cat.isNeutered(),
                cat.getNeuteredDate(),

                cat.getNotes(),

                cat.getCreatedByCatteryId()
        );
    }
}
