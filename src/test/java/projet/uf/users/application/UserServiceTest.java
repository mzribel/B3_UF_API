package projet.uf.users.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projet.uf.modules.user.application.port.in.CreateUserCommand;
import projet.uf.modules.user.application.port.out.UserPersistence;
import projet.uf.modules.user.application.UserServiceImpl;
import projet.uf.modules.user.domain.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserPersistence userPersistencePort;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void shouldCreateUser_WhenValidAndNotExists() {
        // given
        CreateUserCommand command = new CreateUserCommand("test@test.fr", "password", "test", false);
        when(userPersistencePort.existsByEmail("test@test.fr")).thenReturn(false);

        User expectedUser = new User(1L, "test@test.fr", "password", "test", false);
        when(userPersistencePort.save(any(User.class))).thenReturn(expectedUser);

        // when
        User user = userService.createUser(command);

        // then
        assertEquals(expectedUser, user);
        verify(userPersistencePort).existsByEmail("test@test.fr");
        verify(userPersistencePort).save(any(User.class));
    }
}
