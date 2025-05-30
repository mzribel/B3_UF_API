package projet.uf.modules.breeder.application.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import projet.uf.modules.breeder.domain.model.Breeder;

public record UpdateCatteryBreederCommand (
        @NotBlank(message = "Le nom de la chatterie ne peut pas être vide")
        @NotNull(message = "Le nom de la chatterie ne peut pas être vide")
        @Size(max = 30, message = "Le nom de la chatterie ne peut pas dépasser 30 caractères")
        String name,
        String siret,
        String affix,
        boolean isAffixPrefix,
        boolean isActive,
        boolean isDerogatory
) {


    public static Breeder toModel(UpdateCatteryBreederCommand command, Long createdByCatteryId) {
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
