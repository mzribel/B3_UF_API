package projet.uf.modules.auth.application.ports.out;

import projet.uf.modules.user.domain.model.User;

import java.util.Optional;

public interface LoadUserPort {
     Optional<User> getByEmail(String email);
     boolean existsByEmail(String email);
}
