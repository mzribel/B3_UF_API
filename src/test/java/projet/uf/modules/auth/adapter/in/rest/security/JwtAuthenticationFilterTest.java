package projet.uf.modules.auth.adapter.in.rest.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import projet.uf.modules.auth.adapters.in.rest.security.JwtAuthenticationFilter;
import projet.uf.modules.auth.adapters.out.security.HeaderAuthenticationToken;
import projet.uf.modules.auth.adapters.out.security.JwtService;
import projet.uf.modules.auth.application.model.CurrentUser;
import projet.uf.modules.user.application.port.out.UserPersistencePort;
import projet.uf.modules.user.domain.model.User;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private UserPersistencePort userPersistencePort;

    @Mock
    private JwtService jwtService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        jwtAuthenticationFilter = new JwtAuthenticationFilter(userPersistencePort, jwtService);
        SecurityContextHolder.clearContext(); // Clear security context before each test
    }

    @Test
    void doFilter_shouldContinueFilterChain_whenNoAuthorizationHeader() throws ServletException, IOException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        jwtAuthenticationFilter.doFilter(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilter_shouldContinueFilterChain_whenAuthorizationHeaderDoesNotStartWithBearer() throws ServletException, IOException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("Basic dXNlcjpwYXNzd29yZA==");

        // Act
        jwtAuthenticationFilter.doFilter(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilter_shouldContinueFilterChain_whenTokenIsInvalid() throws ServletException, IOException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("Bearer invalid-token");
        when(jwtService.extractUsername(anyString())).thenThrow(new RuntimeException("Invalid token"));

        // Act
        jwtAuthenticationFilter.doFilter(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilter_shouldContinueFilterChain_whenUserNotFound() throws ServletException, IOException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("Bearer valid-token");
        when(jwtService.extractUsername(anyString())).thenReturn("user@example.com");
        when(userPersistencePort.getByEmail(anyString())).thenReturn(Optional.empty());

        // Act
        jwtAuthenticationFilter.doFilter(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilter_shouldSetAuthentication_whenTokenIsValidAndUserExists() throws ServletException, IOException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("Bearer valid-token");
        when(jwtService.extractUsername(anyString())).thenReturn("user@example.com");

        User user = new User(1L, "user@example.com", "encoded", "Test User", false);
        when(userPersistencePort.getByEmail(anyString())).thenReturn(Optional.of(user));

        // Act
        jwtAuthenticationFilter.doFilter(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertTrue(authentication instanceof HeaderAuthenticationToken);

        CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
        assertEquals(1L, currentUser.id());
        assertFalse(currentUser.isAdmin());
    }

    @Test
    void doFilter_shouldSetAuthenticationWithAdminRole_whenUserIsAdmin() throws ServletException, IOException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("Bearer valid-token");
        when(jwtService.extractUsername(anyString())).thenReturn("admin@example.com");

        User adminUser = new User(2L, "admin@example.com", "encoded", "Admin User", true);
        when(userPersistencePort.getByEmail(anyString())).thenReturn(Optional.of(adminUser));

        // Act
        jwtAuthenticationFilter.doFilter(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertTrue(authentication instanceof HeaderAuthenticationToken);

        CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
        assertEquals(2L, currentUser.id());
        assertTrue(currentUser.isAdmin());

        assertTrue(authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN")));
    }
}
