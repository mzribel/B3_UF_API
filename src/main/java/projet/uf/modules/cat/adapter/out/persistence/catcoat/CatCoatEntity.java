package projet.uf.modules.cat.adapter.out.persistence.catcoat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import projet.uf.modules.cat.domain.model.CatCoat;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cat_coats")
public class CatCoatEntity {
    @Column(name = "cat_id", nullable = false)
    @Id
    Long catId;

    @Column(name = "coat_color_id", nullable = false)
    Long coatColorId;

    @Column(name = "coat_pattern_id", nullable = false)
    Long coatPatternId;

    @Column(name = "coat_effect_id", nullable = false)
    Long coatEffectId;

    @Column(name = "coat_white_marking_id", nullable = false)
    Long coatWhiteMarkingId;

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
