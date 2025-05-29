package projet.uf.modules.auth.exception;

import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;

public class WeakPasswordException extends ApiException {
    public WeakPasswordException(String message, HttpStatus status) {
        super(message, status);
    }
}
