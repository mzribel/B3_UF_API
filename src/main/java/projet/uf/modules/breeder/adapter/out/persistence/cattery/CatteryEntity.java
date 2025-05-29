package projet.uf.modules.breeder.adapter.out.persistence.cattery;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "catteries")
public class CatteryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "created_by_user_id")
    private Long createdByUserId;

    @Column(name = "linked_to_breeder_id")
    private Long linkedToBreederId;
}
