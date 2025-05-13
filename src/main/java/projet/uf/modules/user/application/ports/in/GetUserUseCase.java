package projet.uf.modules.user.application.ports.in;

import projet.uf.modules.user.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface GetUserUseCase {
    Optional<User> getById(Long id);
    Optional<User> getByEmail(String email);
    List<User> getAll();
}
