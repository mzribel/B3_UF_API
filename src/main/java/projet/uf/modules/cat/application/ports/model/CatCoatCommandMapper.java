package projet.uf.modules.cat.application.ports.model;

import projet.uf.modules.cat.domain.model.CatCoat;

public class CatCoatCommandMapper {
    public static CatCoat fromCommand(CatCoatCommand command, Long catId) {
        return new CatCoat(
                catId,
                command.colorId(),
                command.patternId(),
                command.effectId(),
                command.whiteMarkingId()
        );
    }
}
