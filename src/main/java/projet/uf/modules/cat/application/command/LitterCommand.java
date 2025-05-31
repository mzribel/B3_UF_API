package projet.uf.modules.cat.application.command;

import jakarta.validation.constraints.NotNull;
import projet.uf.modules.cat.domain.model.Litter;

import java.time.LocalDate;

public record LitterCommand(
        Long sireId,
        Long damId,
        Long originBreederId,
        LocalDate birthDate,
        String loofIdentificationNumber,
        LocalDate loofDeclarationDate,
        int kittenCount,
        String notes
) {
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
