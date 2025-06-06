package projet.uf.modules.cat.model;

import org.junit.jupiter.api.Test;
import projet.uf.modules.cat.domain.model.CatCoat;

import static org.assertj.core.api.Assertions.assertThat;

class CatCoatTest {

    @Test
    void shouldCreateCatCoatWithAllArgsConstructor() {
        // Given
        Long catId = 1L;
        Long coatColorId = 2L;
        Long coatPatternId = 3L;
        Long coatEffectId = 4L;
        Long coatWhiteMarkingId = 5L;

        // When
        CatCoat catCoat = new CatCoat(catId, coatColorId, coatPatternId, coatEffectId, coatWhiteMarkingId);

        // Then
        assertThat(catCoat.getCatId()).isEqualTo(catId);
        assertThat(catCoat.getCoatColorId()).isEqualTo(coatColorId);
        assertThat(catCoat.getCoatPatternId()).isEqualTo(coatPatternId);
        assertThat(catCoat.getCoatEffectId()).isEqualTo(coatEffectId);
        assertThat(catCoat.getCoatWhiteMarkingId()).isEqualTo(coatWhiteMarkingId);
    }
} 