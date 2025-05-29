package projet.uf.modules.breeder.application.port.out;

import projet.uf.modules.user.domain.model.User;

import java.util.Optional;

public interface UserAccessPort {
    Optional<User> getById(Long id);
    Optional<User> getByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsById(Long id);
}
