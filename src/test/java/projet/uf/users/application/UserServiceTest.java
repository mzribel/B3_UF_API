package projet.uf.users.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projet.uf.users.application.ports.in.CreateUserCommand;
import projet.uf.users.application.ports.out.UserPersistencePort;
import projet.uf.users.application.service.UserService;
import projet.uf.users.domain.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserPersistencePort userPersistencePort;
    @InjectMocks
    private UserService userService;

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
