package projet.uf.modules.user.application;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import projet.uf.modules.auth.exception.UserAlreadyExistsException;
import projet.uf.modules.user.application.mapper.UserCommandMapper;
import projet.uf.modules.user.application.port.in.CreateUserCommand;
import projet.uf.modules.user.application.port.in.UserUseCase;
import projet.uf.modules.user.application.port.out.UserPersistencePort;
import projet.uf.modules.user.domain.model.User;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserService implements UserUseCase {
    private final UserPersistencePort userPersistencePort;

    @Override
    public User createUser(CreateUserCommand command) {
        if (userPersistencePort.existsByEmail(command.getEmail())) {
            throw new UserAlreadyExistsException("A user with this email already exists", HttpStatus.CONFLICT);
        }
        User user = UserCommandMapper.fromCreateCommand(command);
        return userPersistencePort.save(user);
    }

    @Override
    public Optional<User> getById(Long id) {
        return userPersistencePort.getById(id);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userPersistencePort.getByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return userPersistencePort.getAll();
    }
}
