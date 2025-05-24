package projet.uf.modules.auth.adapters.out.persistence;

import org.springframework.stereotype.Component;
import projet.uf.modules.user.application.port.out.UserPersistence;
import projet.uf.modules.user.domain.model.User;

import java.util.Optional;

@Component
public class UserPersistenceImpl implements projet.uf.modules.auth.application.ports.out.UserPersistence {
    private final UserPersistence userPersistencePort;

    public UserPersistenceImpl(UserPersistence userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userPersistencePort.getByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userPersistencePort.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return userPersistencePort.save(user);
    }
}
