package projet.uf.modules.cat.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class Litter {
    private Long id;
    private Long damId;
    private Long sireId;
    private Long originBreederId;
    private LocalDate birthDate;
    private String loofIdentificationNumber;
    private LocalDate loofDeclarationDate;
    private int kittenCount;
    private String notes;
    private Long createdByCatteryId;

    // Constructeur sans ID pour la création d'une portée
    public Litter(
            Long damId,
            Long sireId,
            Long originBreederId,
            LocalDate birthDate,
            String loofIdentificationNumber,
            LocalDate loofDeclarationDate,
            int kittenCount,
            String notes,
            Long createdByCatteryId
    ) {
        this.damId = damId;
        this.sireId = sireId;
        this.originBreederId = originBreederId;
        this.birthDate = birthDate;
        this.loofIdentificationNumber = loofIdentificationNumber;
        this.loofDeclarationDate = loofDeclarationDate;
        this.kittenCount = kittenCount;
        this.notes = notes;
        this.createdByCatteryId = createdByCatteryId;

    }

    // TODO : le virer de là
    public Litter() {

    }
}
