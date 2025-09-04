package projet.uf.modules.auth.adapters.in.rest.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.CurrentUser;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class CurrentUserProviderTest {

    @Autowired
    private CurrentUserProvider currentUserProvider;

    @Test
    void shouldThrowExceptionWhenAuthenticationIsNull() {
        // Arrange
        SecurityContextHolder.clearContext();

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, currentUserProvider::getCurrentUser);
        assertEquals("Utilisateur non connecté", exception.getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenAuthenticationIsNotAuthenticated() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, currentUserProvider::getCurrentUser);
        assertEquals("Utilisateur non connecté", exception.getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());

        // Clean up
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldThrowExceptionWhenPrincipalIsNotCurrentUserInstance() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("InvalidPrincipal");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, currentUserProvider::getCurrentUser);
        assertEquals("Impossible de récupérer l'identifiant utilisateur", exception.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());

        // Clean up
        SecurityContextHolder.clearContext();
    }

    @Test
    @WithMockUser
    void shouldReturnCurrentUserWhenPrincipalIsValid() {
        // Arrange
        CurrentUser expectedUser = new CurrentUser(1L, true);
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(expectedUser);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Act
        CurrentUser actualUser = currentUserProvider.getCurrentUser();

        // Assert
        assertNotNull(actualUser);
        assertEquals(expectedUser.id(), actualUser.id());
        assertEquals(expectedUser.isAdmin(), actualUser.isAdmin());

        // Clean up
        SecurityContextHolder.clearContext();
    }
}