package projet.uf.modules.cat.adapter.out.persistence.litter;

import jakarta.persistence.*;
import lombok.*;
import projet.uf.modules.cat.domain.model.Litter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "litters")
public class LitterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dam_id")
    private Long damId;

    @Column(name = "sire_id")
    private Long sireId;

    @Column(name = "origin_breeder_id")
    private Long originBreederId;

    @Column
    private LocalDate birthDate;

    @Column(name = "loof_identification_number")
    private String loofIdentificationNumber;

    @Column(name = "loof_declaration_date")
    private LocalDate loofDeclarationDate;

    @Column(name = "kitten_count")
    private int kittenCount;

    @Column
    private String notes;

    @Column(name = "created_by_cattery_id", nullable = false)
    private Long createdByCatteryId;

    public static Litter toModel(LitterEntity entity) {
        return new Litter(
                entity.id,
                entity.sireId,
                entity.damId,
                entity.originBreederId,
                entity.birthDate,
                entity.loofIdentificationNumber,
                entity.loofDeclarationDate,
                entity.kittenCount,
                entity.notes,
                entity.createdByCatteryId
        );
    }

    public static LitterEntity toEntity(Litter model) {
        return new LitterEntity(
                model.getId(),
                model.getDamId(),
                model.getSireId(),
                model.getOriginBreederId(),
                model.getBirthDate(),
                model.getLoofIdentificationNumber(),
                model.getLoofDeclarationDate(),
                model.getKittenCount(),
                model.getNotes(),
                model.getCreatedByCatteryId()
        );
    }
}
