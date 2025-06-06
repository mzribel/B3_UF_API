package projet.uf.modules.auth.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import projet.uf.modules.auth.adapters.in.rest.dto.AuthenticatedUserDto;
import projet.uf.modules.auth.adapters.out.security.JwtService;
import projet.uf.modules.auth.application.AuthService;
import projet.uf.modules.auth.application.ports.in.LoginCommand;
import projet.uf.modules.auth.application.ports.in.RegisterCommand;
import projet.uf.modules.auth.application.ports.out.PasswordEncoder;
import projet.uf.modules.auth.exception.UserAlreadyExistsException;
import projet.uf.modules.auth.exception.WeakPasswordException;
import projet.uf.modules.auth.exception.WrongCredentialsException;
import projet.uf.modules.user.application.dto.UserDto;
import projet.uf.modules.user.application.port.out.UserPersistencePort;
import projet.uf.modules.user.domain.model.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserPersistencePort userPersistencePort;

    @Mock
    private JwtService jwtService;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        authService = new AuthService(passwordEncoder, userPersistencePort, jwtService);
    }

    @Test
    void register_shouldThrowException_whenEmailAlreadyExists() {
        // Arrange
        RegisterCommand command = new RegisterCommand("existing@example.com", "Password123!", "Test User");
        when(userPersistencePort.existsByEmail(anyString())).thenReturn(true);

        // Act & Assert
        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class,
                () -> authService.register(command));
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        verify(userPersistencePort).existsByEmail("existing@example.com");
        verify(userPersistencePort, never()).save(any());
    }

    @Test
    void register_shouldThrowException_whenPasswordIsTooWeak() {
        // Arrange
        RegisterCommand command = new RegisterCommand("new@example.com", null, "Test User");
        when(userPersistencePort.existsByEmail(anyString())).thenReturn(false);

        // Act & Assert
        WeakPasswordException exception = assertThrows(WeakPasswordException.class,
                () -> authService.register(command));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(userPersistencePort).existsByEmail("new@example.com");
        verify(userPersistencePort, never()).save(any());
    }

    @Test
    void register_shouldCreateUser_whenValidData() {
        // Arrange
        RegisterCommand command = new RegisterCommand("new@example.com", "StrongP@ss1", "Test User");
        User user = new User(null, "new@example.com", "encoded", "Test User", false);
        User savedUser = new User(1L, "new@example.com", "encoded", "Test User", false);

        when(userPersistencePort.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");
        when(userPersistencePort.save(any(User.class))).thenReturn(savedUser);
        when(jwtService.generateToken(any(User.class))).thenReturn("jwt-token");

        // Act
        AuthenticatedUserDto result = authService.register(command);

        // Assert
        assertNotNull(result);
        assertEquals("jwt-token", result.token());
        assertEquals("new@example.com", ((UserDto)result.userDto()).getEmail());
        assertEquals("Test User", result.userDto().getDisplayName());

        verify(userPersistencePort).existsByEmail("new@example.com");
        verify(passwordEncoder).encode("StrongP@ss1");
        verify(userPersistencePort).save(any(User.class));
        verify(jwtService).generateToken(savedUser);
    }

    @Test
    void login_shouldThrowException_whenUserNotFound() {
        // Arrange
        LoginCommand command = new LoginCommand("nonexistent@example.com", "password");
        when(userPersistencePort.getByEmail(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        WrongCredentialsException exception = assertThrows(WrongCredentialsException.class,
                () -> authService.login(command));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(userPersistencePort).getByEmail("nonexistent@example.com");
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void login_shouldThrowException_whenPasswordDoesNotMatch() {
        // Arrange
        LoginCommand command = new LoginCommand("user@example.com", "wrongPassword");
        User user = new User(1L, "user@example.com", "encoded", "Test User", false);

        when(userPersistencePort.getByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // Act & Assert
        WrongCredentialsException exception = assertThrows(WrongCredentialsException.class,
                () -> authService.login(command));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(userPersistencePort).getByEmail("user@example.com");
        verify(passwordEncoder).matches("wrongPassword", "encoded");
    }

    @Test
    void login_shouldReturnAuthenticatedUser_whenCredentialsAreValid() {
        // Arrange
        LoginCommand command = new LoginCommand("user@example.com", "correctPassword");
        User user = new User(1L, "user@example.com", "encoded", "Test User", false);

        when(userPersistencePort.getByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtService.generateToken(any(User.class))).thenReturn("jwt-token");

        // Act
        AuthenticatedUserDto result = authService.login(command);

        // Assert
        assertNotNull(result);
        assertEquals("jwt-token", result.token());
        assertEquals("user@example.com", ((UserDto)result.userDto()).getEmail());
        assertEquals("Test User", result.userDto().getDisplayName());

        verify(userPersistencePort).getByEmail("user@example.com");
        verify(passwordEncoder).matches("correctPassword", "encoded");
        verify(jwtService).generateToken(user);
    }
}
