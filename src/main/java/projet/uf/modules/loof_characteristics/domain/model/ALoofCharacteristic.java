package projet.uf.modules.loof_characteristics.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ALoofCharacteristic {
    private Long id;
    private String code;
    private String name;
    private String details;

    protected ALoofCharacteristic(Long id, String code, String name, String details) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.details = details;
    }
    protected ALoofCharacteristic(String code, String name, String details) {
        this.code = code;
        this.name = name;
        this.details = details;
    }
}
