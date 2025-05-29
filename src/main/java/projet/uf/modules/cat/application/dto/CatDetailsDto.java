package projet.uf.modules.cat.application.dto;

import projet.uf.modules.breeder.domain.model.Breeder;
import projet.uf.modules.cat.domain.model.Cat;

import java.time.LocalDate;

public record CatDetailsDto(
        Long id,
        String name,
        String surname,
        boolean isFemale,
        LocalDate birthDate,
        CatLoofDto dam,
        CatLoofDto sire,
        CatCoatDto coat,
        Breeder originBreeder,
        Breeder currentBreeder,
        String pedigreeNumber,
        String identificationNumber,
        boolean isDeceased,
        LocalDate deceasedDate,
        boolean isNeutered,
        LocalDate neuteredDate,
        Long createdByCatteryId,
        String notes
) {
    public static CatDetailsDto toDto(Cat cat,
                                      CatLoofDto dam,
                                      CatLoofDto sire,
                                      CatCoatDto coat,
                                      Breeder originBreeder,
                                      Breeder currentBreeder
                                      ) {
        return new CatDetailsDto(
                cat.getId(),
                cat.getName(),
                cat.getSurname(),
                cat.isSex(),
                cat.getBirthDate(),
                dam,
                sire,
                coat,
                originBreeder,
                currentBreeder,
                cat.getPedigreeNo(),
                cat.getIdentificationNo(),
                cat.isDeceased(),
                cat.getDeceasedDate(),
                cat.isNeutered(),
                cat.getNeuteredDate(),
                cat.getCreatedByCatteryId(),
                cat.getNotes()
        );
    }
}
