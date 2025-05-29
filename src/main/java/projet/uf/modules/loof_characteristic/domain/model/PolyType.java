package projet.uf.modules.loof_characteristic.domain.model;

public class PolyType extends ALoofCharacteristic {
    public PolyType(Long id, String code, String name, String details) {
        super(id, code, name, details);
    }
    public PolyType(String code, String name, String details) {
        super(code, name, details);
    }
}
