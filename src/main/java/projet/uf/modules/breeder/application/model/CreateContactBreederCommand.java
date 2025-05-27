package projet.uf.modules.breeder.application.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
}
