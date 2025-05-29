package projet.uf.modules.cat.application.dto;

import projet.uf.modules.breeder.domain.model.Breeder;
import projet.uf.modules.cat.domain.model.Cat;

import java.time.LocalDate;

public record CatLoofDto(
        Long id,
        Long catteryId,
        String name,
        String poly_type,
        String affix,
        boolean isAffixPrefix,
        String race,
        String color,
        String code,
        LocalDate birthDate,
        boolean isFemale,
        String pedigreeNumber,
        String identificationNumber,
        CatLoofDto sire,
        CatLoofDto dam
) {
    public static CatLoofDto toDto(Cat cat,
                                   CatLoofDto dam,
                                   CatLoofDto sire,
                                   CatCoatDto coat,
                                   Breeder originBreeder
                                   ) {

        return new CatLoofDto(
                cat.getId(),
                cat.getCreatedByCatteryId(),
                cat.getName(),
                null,
                getAffix(originBreeder),
                getIsAffixSuffix(originBreeder),
                "Maine Coon",
                getLabel(coat),
                getCode(coat),
                cat.getBirthDate(),
                cat.isSex(),
                cat.getPedigreeNo(),
                cat.getIdentificationNo(),
                sire,
                dam
        );
    }

    private static String getAffix(Breeder breeder) {
        return (breeder != null && breeder.getAffix() != null) ? breeder.getAffix() : "";
    }
    private static boolean getIsAffixSuffix(Breeder breeder) {
        return (breeder != null && breeder.isAffixPrefix());
    }
    private static String getCode(CatCoatDto coat) {
        return (coat != null && coat.code() != null) ? coat.code() : "";
    }
    private static String getLabel(CatCoatDto coat) {
        return (coat != null && coat.label() != null) ? coat.label() : "";
    }
}
