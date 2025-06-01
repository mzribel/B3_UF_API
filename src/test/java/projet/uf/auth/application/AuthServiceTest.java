package projet.uf.auth.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projet.uf.modules.auth.adapters.in.rest.dto.AuthenticatedUserDto;
import projet.uf.modules.auth.application.ports.in.LoginCommand;
import projet.uf.modules.auth.application.ports.out.PasswordEncoder;
import projet.uf.modules.auth.application.AuthService;
import projet.uf.modules.auth.application.ports.in.RegisterCommand;
import projet.uf.modules.auth.exception.UserAlreadyExistsException;
import projet.uf.modules.auth.exception.WeakPasswordException;
import projet.uf.modules.auth.exception.WrongCredentialsException;
import projet.uf.modules.user.application.port.out.UserPersistencePort;
import projet.uf.modules.user.domain.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserPersistencePort userPersistencePort;
    @InjectMocks
    private AuthService authService;

    @Test
    public void register_shouldCreateAndReturnUser_whenEmailNotExistsAndPasswordIsStrong() {
        // Arrange
        String rawPassword = "Soleil123€";
        String encodedPassword = "ENCODED_Soleil123€";
        RegisterCommand command = new RegisterCommand("test@test.fr", rawPassword, "Test");

//        when(userPersistencePort.existsByEmail(command.email())).thenReturn(false);
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        // Simuler le retour de save avec l'objet que l'application produit
        // (on ne connaît pas exactement l'objet puisque c'est authService qui le construit)
        when(userPersistencePort.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        AuthenticatedUserDto result = authService.register(command);

        // Assert
        assertEquals(command.email(), result.userDto().email());
        assertEquals(command.displayName(), result.userDto().displayName());

        // Vérifier que le User passé à save contient bien le mot de passe encodé
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
//        verify(userPersistencePort).save(captor.capture());

        User savedUser = captor.getValue();
        assertEquals(command.email(), savedUser.getEmail());
        assertEquals(encodedPassword, savedUser.getPassword());
        assertEquals(command.displayName(), savedUser.getDisplayName());
    }

    @Test
    public void register_shouldThrowUserAlreadyExistsException_whenEmailAlreadyExists() {
        // Arrange
        RegisterCommand command = new RegisterCommand("test@test.fr", "Soleil123€", "Test");
//        when(userPersistencePort.existsByEmail(command.email())).thenReturn(true);

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> authService.register(command));
    }

    @Test
    public void register_shouldThrowWeakPasswordException_whenPasswordIsWeak() {
        // Arrange
        String weakPassword = "abc";
        RegisterCommand command = new RegisterCommand("test@test.fr", weakPassword, "Test");

//        when(userPersistencePort.existsByEmail(command.email())).thenReturn(false);

        // Act & Assert
        assertThrows(WeakPasswordException.class, () -> authService.register(command));
    }

    @Test
    public void login_shouldReturnUser_whenCredentialsAreCorrect() {
        // Arrange
        String rawPassword = "Soleil123€";
        String encodedPassword = "ENCODED_Soleil123€";
        LoginCommand command = new LoginCommand("test@test.fr", rawPassword);
        User user = new User(command.email(), encodedPassword, "Test");

//        when(userPersistencePort.getByEmail(command.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        // Act
        AuthenticatedUserDto result = authService.login(command);

        // Assert
        assertEquals(user.getEmail(), result.userDto().email());
        assertEquals(user.getDisplayName(), result.userDto().displayName());
    }

    @Test
    public void login_shouldThrowWrongCredentialsException_whenEmailNotFound() {
        // Arrange
        LoginCommand command = new LoginCommand("unknown@test.fr", "Soleil123€");

//        when(userPersistencePort.getByEmail(command.email())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(WrongCredentialsException.class, () -> authService.login(command));
    }

    @Test
    public void login_shouldThrowWrongCredentialsException_whenPasswordDoesNotMatch() {
        // Arrange
        String rawPassword = "WrongPass";
        String encodedPassword = "ENCODED_Soleil123€";
        LoginCommand command = new LoginCommand("test@test.fr", rawPassword);
        User user = new User(command.email(), encodedPassword, "Test");

//        when(userPersistencePort.getByEmail(command.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        // Act & Assert
        assertThrows(WrongCredentialsException.class, () -> authService.login(command));
    }
}
