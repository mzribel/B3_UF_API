package projet.uf.modules.auth.exception;

import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;

public class WrongCredentialsException extends ApiException {
    public WrongCredentialsException(String message, HttpStatus status) {
        super(message, status);
    }
}
