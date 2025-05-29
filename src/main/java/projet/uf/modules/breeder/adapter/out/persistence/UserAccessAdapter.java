package projet.uf.modules.breeder.adapter.out.persistence;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import projet.uf.modules.breeder.application.port.out.UserAccessPort;
import projet.uf.modules.user.application.port.out.UserPersistencePort;
import projet.uf.modules.user.domain.model.User;

import java.util.Optional;

@Component("catteryUserAccessAdapter")
@AllArgsConstructor
public class UserAccessAdapter implements UserAccessPort {
    private final UserPersistencePort userPersistencePort;

    @Override
    public Optional<User> getById(Long id) {
        return userPersistencePort.getById(id);
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
    public boolean existsById(Long id) {
        return userPersistencePort.existsById(id);
    }
}
