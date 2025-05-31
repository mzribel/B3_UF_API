package projet.uf.modules.cat.adapter.out.persistence.cat;

import jakarta.persistence.*;
import lombok.*;
import projet.uf.modules.cat.domain.model.Cat;

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
    @Builder.Default
    private boolean sex = false;

    @Column(name = "litter_id")
    private Long litterId;

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
    @Builder.Default
    private boolean isNeutered = false;

    @Column(name = "neutered_date")
    private LocalDate neuteredDate;

    @Column(name = "is_deceased", nullable = false)
    @Builder.Default
    private boolean isDeceased = false;

    @Column(name = "deceased_date")
    private LocalDate deceasedDate;

    @Column(name = "created_by_cattery_id", nullable = false)
    private Long createdByCatteryId;

    @Column(name = "is_in_cattery", nullable = false)
    @Builder.Default
    private boolean isInCattery = true;

    @Column(columnDefinition = "TEXT")
    private String notes;

    public static Cat toModel(CatEntity entity) {
        return new Cat(
                entity.getId(),
                entity.getName(),
                entity.getSurname(),
                entity.isSex(),
                entity.getLitterId(),
                entity.getBreedId(),
                entity.getEyeColorId(),
                entity.getPolyTypeId(),
                entity.getPedigreeNo(),
                entity.getIdentificationNo(),
                entity.isNeutered(),
                entity.getNeuteredDate(),
                entity.isDeceased(),
                entity.getDeceasedDate(),
                entity.getCreatedByCatteryId(),
                entity.isInCattery(),
                entity.getNotes()
        );
    }

    public static CatEntity toEntity(Cat model) {
        return CatEntity.builder()
                .name(model.getName())
                .surname(model.getSurname())
                .sex(model.isSex())
                .litterId(model.getLitterId())
                .breedId(model.getBreedId())
                .eyeColorId(model.getEyeColorId())
                .polyTypeId(model.getPolyTypeId())
                .pedigreeNo(model.getPedigreeNo())
                .identificationNo(model.getIdentificationNo())
                .isNeutered(model.isNeutered())
                .neuteredDate(model.getNeuteredDate())
                .isDeceased(model.isDeceased())
                .deceasedDate(model.getDeceasedDate())
                .createdByCatteryId(model.getCreatedByCatteryId())
                .isInCattery(model.isInCattery())
                .notes(model.getNotes())
                .build();
    }
}
