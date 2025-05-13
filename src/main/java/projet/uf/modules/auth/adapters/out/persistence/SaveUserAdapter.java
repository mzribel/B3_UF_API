package projet.uf.modules.auth.adapters.out.persistence;

import org.springframework.stereotype.Component;
import projet.uf.modules.auth.application.ports.out.LoadUserPort;
import projet.uf.modules.auth.application.ports.out.SaveUserPort;
import projet.uf.modules.user.application.ports.out.UserPersistencePort;
import projet.uf.modules.user.domain.model.User;

import java.util.Optional;

@Component
public class SaveUserAdapter implements SaveUserPort {
    private final UserPersistencePort userPersistencePort;

    public SaveUserAdapter(UserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public User save(User user) {
        return userPersistencePort.save(user);
    }
}
