package projet.uf.modules.auth.exception;

import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;

public class UserAlreadyExistsException extends ApiException {
    public UserAlreadyExistsException(String message, HttpStatus status) {
        super(message, status);
    }
}
