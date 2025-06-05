package projet.uf.modules.auth.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import projet.uf.modules.auth.exception.UserAlreadyExistsException;
import projet.uf.modules.auth.exception.WeakPasswordException;
import projet.uf.modules.auth.exception.WrongCredentialsException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthExceptionsTest {

    @Test
    void userAlreadyExistsException_shouldHaveCorrectMessageAndStatus() {
        // Arrange
        String errorMessage = "User already exists";
        HttpStatus status = HttpStatus.CONFLICT;

        // Act
        UserAlreadyExistsException exception = new UserAlreadyExistsException(errorMessage, status);

        // Assert
        assertEquals(errorMessage, exception.getMessage());
        assertEquals(status, exception.getStatus());
    }

    @Test
    void weakPasswordException_shouldHaveCorrectMessageAndStatus() {
        // Arrange
        String errorMessage = "Password is too weak";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        // Act
        WeakPasswordException exception = new WeakPasswordException(errorMessage, status);

        // Assert
        assertEquals(errorMessage, exception.getMessage());
        assertEquals(status, exception.getStatus());
    }

    @Test
    void wrongCredentialsException_shouldHaveCorrectMessageAndStatus() {
        // Arrange
        String errorMessage = "Invalid credentials";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        // Act
        WrongCredentialsException exception = new WrongCredentialsException(errorMessage, status);

        // Assert
        assertEquals(errorMessage, exception.getMessage());
        assertEquals(status, exception.getStatus());
    }
}