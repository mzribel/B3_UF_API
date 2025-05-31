package projet.uf.modules.cat.application.dto;

import projet.uf.modules.loof_characteristic.domain.model.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public record CatCoatDto(
        String breed,
        String color,
        String code,
        String eyes,
        String polydactylism
) {

    public static CatCoatDto toDto(
            Breed breed,
            CoatColor color,
            CoatPattern pattern,
            CoatEffect effect,
            CoatWhiteMarking whiteMarking,
            PolyType polyType
    ) {
        if (breed == null) {
            breed = new Breed("MCO", "Maine Coon", "");
        }

        return new CatCoatDto(
                breed != null ? breed.getName() : null,
                composeColour(color, pattern, effect, whiteMarking),
                composeCode(breed, color, pattern, effect, whiteMarking),
                // TODO : eyes
                null,
                polyType != null ? polyType.getName() : null
        );
    }

    private static String composeColour(
            CoatColor color,
            CoatPattern pattern,
            CoatEffect effect,
            CoatWhiteMarking whiteMarking
    ) {
        if (color == null) { return null;}

        return Stream.of(
                        color.getName(),
                        pattern != null ? pattern.getName() : null,
                        effect != null ? effect.getName() : null,
                        whiteMarking != null ? whiteMarking.getName() : null
                )
                .filter(s -> s != null && !s.isBlank())
                .collect(Collectors.joining(" "));
    }

    private static String composeCode(
            Breed breed,
            CoatColor color,
            CoatPattern pattern,
            CoatEffect effect,
            CoatWhiteMarking whiteMarking
    ) {
        if (breed == null || color == null) { return null;}

        return Stream.of(
                        breed.getCode(),
                        (color.getCode() + effect.getCode()),
                        pattern.getCode(),
                        whiteMarking != null ? whiteMarking.getCode() : null
                )
                .filter(s -> s != null && !s.isBlank())
                .collect(Collectors.joining(" "));
    }
}