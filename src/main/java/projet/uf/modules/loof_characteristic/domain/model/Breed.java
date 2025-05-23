package projet.uf.modules.loof_characteristic.domain.model;

public class Breed extends ALoofCharacteristic {
    public Breed(Long id, String code, String name, String details) {
        super(id, code, name, details);
    }
    public Breed(String code, String name, String details) {
        super(code, name, details);
    }
}
