package projet.uf.modules.loof_characteristics.domain.model;

import projet.uf.modules.loof_characteristics.domain.model.ALoofCharacteristic;

public class CoatColor extends ALoofCharacteristic {
    public CoatColor(Long id, String code, String name, String details) {
        super(id, code, name, details);
    }
    public CoatColor(String code, String name, String details) {
        super(code, name, details);
    }
}
