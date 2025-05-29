package projet.uf.modules.cat.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CatCoat {
    private Long catId;
    private Long coatColorId;
    private Long coatPatternId;
    private Long coatEffectId;
    private Long coatWhiteMarkingId;
}
