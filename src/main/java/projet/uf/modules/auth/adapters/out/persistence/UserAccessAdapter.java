package projet.uf.modules.auth.adapters.out.persistence;

import org.springframework.stereotype.Component;
import projet.uf.modules.auth.application.ports.out.UserAccessPort;
import projet.uf.modules.user.application.port.out.UserPersistencePort;
import projet.uf.modules.user.domain.model.User;

import java.util.Optional;

@Component
public class UserAccessAdapter implements UserAccessPort {
    private final UserPersistencePort userPersistencePort;

    public UserAccessAdapter(UserPersistencePort userPersistencePort) {
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
