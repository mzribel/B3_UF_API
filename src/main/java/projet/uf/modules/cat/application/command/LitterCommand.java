package projet.uf.modules.cat.application.command;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projet.uf.modules.cat.domain.model.Litter;

import java.time.LocalDate;


@NoArgsConstructor
@Getter
@Setter
public class LitterCommand {

    private Long sireId;
    private Long damId;
    private Long originBreederId;
    private LocalDate birthDate;
    private String loofIdentificationNumber;
    private LocalDate loofDeclarationDate;
    private int kittenCount;
    private String notes;

    public LitterCommand(Long sireId,
                         Long damId,
                         Long originBreederId,
                         LocalDate birthDate,
                         String loofIdentificationNumber,
                         LocalDate loofDeclarationDate,
                         int kittenCount,
                         String notes) {
        this.sireId = sireId;
        this.damId = damId;
        this.originBreederId = originBreederId;
        this.birthDate = birthDate;
        this.loofIdentificationNumber = loofIdentificationNumber;
        this.loofDeclarationDate = loofDeclarationDate;
        this.kittenCount = kittenCount;
        this.notes = notes;
    }

    // --- Méthode de transformation ---
    public Litter toModel(@NotNull Long catteryId) {
        return new Litter(
                this.sireId,
                this.damId,
                this.originBreederId,
                this.birthDate,
                this.loofIdentificationNumber,
                this.loofDeclarationDate,
                this.kittenCount,
                this.notes,
                catteryId
        );
    }
}

