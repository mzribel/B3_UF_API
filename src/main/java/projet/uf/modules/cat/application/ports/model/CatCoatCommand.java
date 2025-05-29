package projet.uf.modules.cat.application.ports.model;

import jakarta.validation.constraints.NotNull;

public record CatCoatCommand(
        @NotNull(message = "La couleur ne peut pas être nulle")
        Long colorId,
        Long patternId,
        Long effectId,
        Long whiteMarkingId
) {
}
