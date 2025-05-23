package projet.uf.modules.loof_characteristics.domain.model;

public class CoatColor extends ALoofCharacteristic {
    public CoatColor(Long id, String code, String name, String details) {
        super(id, code, name, details);
    }
    public CoatColor(String code, String name, String details) {
        super(code, name, details);
    }
}
