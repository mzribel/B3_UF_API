package projet.uf.modules.cat.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CatCoat {
    private Long cat_id;
    private Long coat_color_id;
    private Long coat_pattern_id;
    private Long coat_effect_id;
    private Long coat_white_marking_id;
}
