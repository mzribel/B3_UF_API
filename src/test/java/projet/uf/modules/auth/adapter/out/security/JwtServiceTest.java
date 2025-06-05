package projet.uf.modules.auth.adapter.out.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projet.uf.modules.auth.adapters.out.security.JwtService;
import projet.uf.modules.user.domain.model.User;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    private static final String TEST_SECRET_KEY =
            "0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    // Utilitaire : récupère la clé secrète utilisée dans JwtService
    private Key getTestSigningKey() {
        return Keys.hmacShaKeyFor(TEST_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void generateToken_shouldContainExpectedClaims() {
        // Arrange
        User user = new User(42L, "test@test.fr", "hashedPassword", "Test", false);

        // Act
        String token = jwtService.generateToken(user);

        // Assert
        assertNotNull(token);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getTestSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals("test@test.fr", claims.getSubject());
        assertEquals(42, claims.get("userId", Integer.class)); // adapter si id est Long
        assertEquals(false, claims.get("admin", Boolean.class));
    }

    @Test
    void extractUsername_shouldReturnSubjectFromToken() {
        // Arrange
        String token = jwtService.generateToken("alice@example.com", Map.of());

        // Act
        String subject = jwtService.extractUsername(token);

        // Assert
        assertEquals("alice@example.com", subject);
    }

    @Test
    void isTokenExpired_shouldReturnFalseForValidToken() {
        // Arrange
        String token = jwtService.generateToken("bob@example.com", Map.of());

        // Act
        boolean expired = jwtService.isTokenExpired(token);

        // Assert
        assertFalse(expired);
    }

    @Test
    void isTokenExpired_shouldReturnTrueForManuallyExpiredToken() {
        // Arrange
        String token = Jwts.builder()
                .setSubject("expired@example.com")
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 2)) // -2h
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60)) // -1min
                .signWith(getTestSigningKey())
                .compact();

        // Act
        boolean expired = jwtService.isTokenExpired(token);

        // Assert
        assertTrue(expired);
    }

    @Test
    void isTokenExpired_shouldReturnTrue_whenTokenHasInvalidSignature() {
        // Arrange
        String fakeSecret = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        Key wrongKey = Keys.hmacShaKeyFor(fakeSecret.getBytes(StandardCharsets.UTF_8));

        String tokenWithWrongKey = Jwts.builder()
                .setSubject("forged@example.com")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(wrongKey, SignatureAlgorithm.HS512)
                .compact();

        // Act
        boolean expired = jwtService.isTokenExpired(tokenWithWrongKey);

        // Assert
        assertTrue(expired); // ✅ car mauvaise signature
    }
}
