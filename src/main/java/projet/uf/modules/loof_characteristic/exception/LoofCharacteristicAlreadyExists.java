package projet.uf.modules.loof_characteristic.exception;

import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;

public class LoofCharacteristicAlreadyExists extends ApiException {
    public LoofCharacteristicAlreadyExists(String message, HttpStatus status) {
        super(message, status);
    }
}
