package projet.uf.modules.breeders.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class Cattery {
    private Long id;
    private Long createdByUserId;
    private Long linkedToBreederId;
}
