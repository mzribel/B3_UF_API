package projet.uf.users.application.service;

import projet.uf.users.application.mapper.UserCommandMapper;
import projet.uf.users.application.ports.in.CreateUserUseCase;
import projet.uf.users.application.ports.in.GetUserUseCase;
import projet.uf.users.application.ports.in.CreateUserCommand;
import projet.uf.users.application.ports.out.UserPersistencePort;
import projet.uf.users.domain.model.User;

import java.util.List;
import java.util.Optional;

public class UserService implements
        CreateUserUseCase,
        GetUserUseCase
{
    private final UserPersistencePort userPersistencePort;

    public UserService(UserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public User createUser(CreateUserCommand command) {
        if (userPersistencePort.existsByEmail(command.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
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
