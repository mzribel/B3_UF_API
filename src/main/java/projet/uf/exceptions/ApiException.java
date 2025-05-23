package projet.uf.exceptions;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    public HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public ApiException(String message) {
        super(message);
    }
    public ApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
