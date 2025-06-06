package projet.uf.modules.auth.adapter.out.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import projet.uf.modules.auth.adapters.out.security.HeaderAuthenticationToken;
import projet.uf.modules.auth.application.model.CurrentUser;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class HeaderAuthenticationTokenTest {

    @Test
    void constructor_shouldCreateTokenWithUserRole() {
        // Arrange
        CurrentUser regularUser = new CurrentUser(1L, false);

        // Act
        HeaderAuthenticationToken token = new HeaderAuthenticationToken(regularUser);

        // Assert
        assertTrue(token.isAuthenticated());
        assertEquals(regularUser, token.getPrincipal());
        assertNull(token.getCredentials());
        
        Collection<GrantedAuthority> authorities = token.getAuthorities();
        assertEquals(1, authorities.size());
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("USER")));
        assertFalse(authorities.stream().anyMatch(a -> a.getAuthority().equals("ADMIN")));
    }

    @Test
    void constructor_shouldCreateTokenWithUserAndAdminRoles_whenUserIsAdmin() {
        // Arrange
        CurrentUser adminUser = new CurrentUser(2L, true);

        // Act
        HeaderAuthenticationToken token = new HeaderAuthenticationToken(adminUser);

        // Assert
        assertTrue(token.isAuthenticated());
        assertEquals(adminUser, token.getPrincipal());
        assertNull(token.getCredentials());
        
        Collection<GrantedAuthority> authorities = token.getAuthorities();
        assertEquals(2, authorities.size());
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("USER")));
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("ADMIN")));
    }

    @Test
    void getCredentials_shouldReturnNull() {
        // Arrange
        CurrentUser user = new CurrentUser(1L, false);
        HeaderAuthenticationToken token = new HeaderAuthenticationToken(user);

        // Act
        Object credentials = token.getCredentials();

        // Assert
        assertNull(credentials);
    }

    @Test
    void getPrincipal_shouldReturnCurrentUser() {
        // Arrange
        CurrentUser user = new CurrentUser(1L, false);
        HeaderAuthenticationToken token = new HeaderAuthenticationToken(user);

        // Act
        Object principal = token.getPrincipal();

        // Assert
        assertEquals(user, principal);
        assertTrue(principal instanceof CurrentUser);
        assertEquals(1L, ((CurrentUser) principal).id());
        assertFalse(((CurrentUser) principal).isAdmin());
    }
}