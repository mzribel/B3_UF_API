package projet.uf.modules.breeder.application.dto;

import projet.uf.modules.breeder.domain.model.Breeder;

public record BreederSummaryDto(
        Long id,
        String affix,
        boolean isAffixSuffix
) {
    public static BreederSummaryDto toDto(Breeder breeder) {
        if (breeder == null) return null;
        return new BreederSummaryDto(
                breeder.getId(),
                breeder.getAffix(),
                breeder.isAffixPrefix()
        );
    }
}
