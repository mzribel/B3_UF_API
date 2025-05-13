package projet.uf.modules.loof_characteristics.domain.model;

public class CoatEffect extends ALoofCharacteristic {
    public CoatEffect(Long id, String code, String name, String details) {
        super(id, code, name, details);
    }
    public CoatEffect(String code, String name, String details) {
        super(code, name, details);
    }
}
