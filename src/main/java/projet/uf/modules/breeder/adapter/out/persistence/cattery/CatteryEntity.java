package projet.uf.modules.breeder.adapter.out.persistence.cattery;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CatteryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long createdByUserId;

    @Column
    private Long linkedToBreederId;

    public CatteryEntity(Long createdByUserId, Long linkedToBreederId) {
        this.createdByUserId = createdByUserId;
        this.linkedToBreederId = linkedToBreederId;
    }
}
