package projet.uf.modules.breeder.application.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import projet.uf.modules.breeder.domain.model.Breeder;

@Getter
@AllArgsConstructor
public class CreateContactBreederCommand {
    @Size(max = 30, message = "Le nom de l'éleveur ne peut pas dépasser 30 caractères")
    private String name;
    private String siret;
    @NotNull(message = "L'affixe de l'éleveur ne peut pas être vide")
    @NotBlank(message = "L'affixe de l'éleveur ne peut pas être vide")
    private String affix;
    private boolean isAffixPrefix;
    private boolean isActive;
    private boolean isDerogatory;

    public static Breeder toModel(CreateContactBreederCommand command, Long createdByCatteryId) {
        return new Breeder(
                command.getName(),
                command.getSiret(),
                command.getAffix(),
                command.isAffixPrefix(),
                null,
                null,
                createdByCatteryId,
                command.isActive(),
                command.isDerogatory()
        );
    }
}
