package projet.uf.modules.cat.application.command;

import jakarta.validation.constraints.NotNull;
import projet.uf.modules.cat.domain.model.CatCoat;

public record CatCoatCommand(
        @NotNull(message = "La couleur ne peut pas être nulle")
        Long colorId,
        Long patternId,
        Long effectId,
        Long whiteMarkingId
) {
        public static CatCoat toModel(CatCoatCommand command, Long catId) {
                return new CatCoat(
                        catId,
                        command.colorId(),
                        command.patternId(),
                        command.effectId(),
                        command.whiteMarkingId()
                );
        }
}
