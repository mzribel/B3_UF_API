package projet.uf.modules.cat.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CatCoat {
    private Long catId;
    private Long coatColorId;
    private Long coatPatternId;
    private Long coatEffectId;
    private Long coatWhiteMarkingId;
}
