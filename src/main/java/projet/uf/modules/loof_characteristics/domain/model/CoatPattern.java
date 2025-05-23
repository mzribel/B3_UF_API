package projet.uf.modules.loof_characteristics.domain.model;

public class CoatPattern extends ALoofCharacteristic {
    public CoatPattern(Long id, String code, String name, String details) {
        super(id, code, name, details);
    }
    public CoatPattern(String code, String name, String details) {
        super(code, name, details);
    }
}
