package projet.uf.modules.auth.adapters.out.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class BCryptPasswordEncoderTest {

    /**
     * Test class for the BCryptPasswordEncoder's matches method.
     * The matches method is used to compare a raw password with an already encoded password.
     */

    @Test
    void testMatches_WithCorrectPassword_ReturnsTrue() {
        // Arrange
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "securePassword123";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Act
        boolean result = passwordEncoder.matches(rawPassword, encodedPassword);

        // Assert
        assertTrue(result, "The matches method should return 'true' for a correct password.");
    }

    @Test
    void testMatches_WithIncorrectPassword_ReturnsFalse() {
        // Arrange
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "securePassword123";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        String incorrectPassword = "wrongPassword456";

        // Act
        boolean result = passwordEncoder.matches(incorrectPassword, encodedPassword);

        // Assert
        assertFalse(result, "The matches method should return 'false' for an incorrect password.");
    }

    @Test
    void testMatches_WithEmptyPasswordAndEncodedPassword_ReturnsFalse() {
        // Arrange
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "";
        String encodedPassword = passwordEncoder.encode("nonEmptyPassword");

        // Act
        boolean result = passwordEncoder.matches(rawPassword, encodedPassword);

        // Assert
        assertFalse(result, "The matches method should return 'false' for an empty raw password.");
    }

    @Test
    void testMatches_WithNullEncodedPassword_ThrowsException() {
        // Arrange
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "testPassword";

        // Act & Assert
        try {
            passwordEncoder.matches(rawPassword, null);
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage().contains("Encoded password cannot be null"),
                    "Expected IllegalArgumentException for null encoded password.");
        }
    }

    @Test
    void testMatches_WithNullRawPassword_ThrowsIllegalArgumentException() {
        // Arrange
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("nonNullPassword");

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> passwordEncoder.matches(null, encodedPassword),
                "Spring BCryptPasswordEncoder lève IllegalArgumentException si le mot de passe brut est null.");
    }

}