package projet.uf.modules.cat.adapter.out.persistence.catcoat;

import projet.uf.modules.cat.domain.model.CatCoat;

public class CatCoatEntityMapper {

    public static CatCoat toModel(CatCoatEntity entity) {
        return new CatCoat(
                entity.catId,
                entity.coatColorId,
                entity.coatPatternId,
                entity.coatEffectId,
                entity.coatWhiteMarkingId
        );
    }

    public static CatCoatEntity toEntity(CatCoat model) {
        return new CatCoatEntity(
                model.getCatId(),
                model.getCoatColorId(),
                model.getCoatPatternId(),
                model.getCoatEffectId(),
                model.getCoatWhiteMarkingId()
        );
    }
}
