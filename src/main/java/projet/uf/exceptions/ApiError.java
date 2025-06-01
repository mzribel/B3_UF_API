package projet.uf.exceptions;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public record ApiError(
        int status,
        String error,
        String message,
        String path,
        String timestamp
) {
    public static ApiError from(HttpStatus status, String message, String path) {
        return new ApiError(
                status.value(),
                status.getReasonPhrase(),
                message,
                path,
                Instant.now().toString()
        );
    }
}
