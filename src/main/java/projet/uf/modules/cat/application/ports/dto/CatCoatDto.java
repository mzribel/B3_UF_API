package projet.uf.modules.cat.application.ports.dto;

import projet.uf.modules.loof_characteristic.domain.model.CoatColor;
import projet.uf.modules.loof_characteristic.domain.model.CoatEffect;
import projet.uf.modules.loof_characteristic.domain.model.CoatPattern;
import projet.uf.modules.loof_characteristic.domain.model.CoatWhiteMarking;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public record CatCoatDto(String code, String label) {

    public static CatCoatDto toDto(
            String breedCode,
            String breedLabel,
            CoatColor color,
            CoatPattern pattern,
            CoatEffect effect,
            CoatWhiteMarking whiteMarking
    ) {
        if (color == null) {
            throw new IllegalArgumentException("La couleur est obligatoire");
        }

        String code = Stream.of(
                        breedCode,
                        color.getCode(),
                        whiteMarking != null ? whiteMarking.getCode() : null
                )
                .filter(s -> s != null && !s.isBlank())
                .collect(Collectors.joining(" "));

        String label = Stream.of(
                        breedLabel,
                        color.getName(),
                        pattern != null ? pattern.getName() : null,
                        effect != null ? effect.getName() : null
                )
                .filter(s -> s != null && !s.isBlank())
                .collect(Collectors.joining(" "));

        return new CatCoatDto(code, label);
    }
}