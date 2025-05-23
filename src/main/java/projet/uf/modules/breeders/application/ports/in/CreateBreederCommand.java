package projet.uf.modules.breeders.application.ports.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateBreederCommand {
    private String name;
    private String siret;
    private String affix;
    private boolean isAffixPrefix;
    private boolean isActive;
    private boolean isDerogatory;
}
