package projet.uf.modules.cat.application.dto;

import projet.uf.modules.breeder.application.dto.BreederSummaryDto;
import projet.uf.modules.cat.domain.model.Litter;

import java.time.LocalDate;

public record LitterSummaryDto(
        Long litterId,
        LocalDate birthDate,
        Long sireId,
        Long damId,
        BreederSummaryDto originBreeder
) {
    public static LitterSummaryDto toDto(Litter litter, BreederSummaryDto originBreeder) {
        if (litter == null) return null;
        return new LitterSummaryDto(
                litter.getId(),
                litter.getBirthDate(),
                litter.getSireId(),
                litter.getDamId(),
                originBreeder
        );
    }
}
