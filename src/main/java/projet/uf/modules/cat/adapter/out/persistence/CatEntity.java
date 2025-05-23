package projet.uf.modules.cat.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cats")
public class CatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String surname;

    @Column(nullable = false)
    private boolean sex = false;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "litter_id")
    private Long litterId;

    @Column(name = "origin_breeder_id")
    private Long originBreederId;

    @Column(name = "current_breeder_id")
    private Long currentBreederId;

    @Column(name = "breed_id")
    private Long breedId;

    @Column(name = "eye_color_id")
    private Long eyeColorId;

    @Column(name = "poly_type_id")
    private Long polyTypeId;

    @Column(name = "pedigree_no")
    private String pedigreeNo;

    @Column(name = "identification_no")
    private String identificationNo;

    @Column(name = "is_neutered", nullable = false)
    private boolean isNeutered = false;

    @Column(name = "neutered_date")
    private LocalDate neuteredDate;

    @Column(name = "is_deceased", nullable = false)
    private boolean isDeceased = false;

    @Column(name = "deceased_date")
    private LocalDate deceasedDate;

    @Column(name = "created_by_cattery_id")
    private Long createdByCatteryId;        // TODO : nullable = false

    @Column(name = "is_in_cattery", nullable = false)
    private boolean isInCattery = true;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
