package projet.uf.modules.breeder.application.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import projet.uf.modules.breeder.domain.model.Breeder;

public record CreateContactBreederCommand (
        @Size(max = 30, message = "Le nom de l'éleveur ne peut pas dépasser 30 caractères")
        String name,
        String siret,
        @NotNull(message = "L'affixe de l'éleveur ne peut pas être vide")
        @NotBlank(message = "L'affixe de l'éleveur ne peut pas être vide")
        String affix,
        boolean isAffixPrefix,
        boolean isActive,
        boolean isDerogatory
){

    public static Breeder toModel(CreateContactBreederCommand command, Long createdByCatteryId) {
        return new Breeder(
                command.name(),
                command.siret(),
                command.affix(),
                command.isAffixPrefix(),
                null,
                null,
                createdByCatteryId,
                command.isActive(),
                command.isDerogatory()
        );
    }
}
