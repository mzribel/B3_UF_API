package projet.uf.modules.user.application.port.out;

import projet.uf.modules.user.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserPersistencePort {
    // Récupération
    Optional<User> getById(Long id);
    Optional<User> getByEmail(String email);
    List<User> getAll();

    // Vérification
    boolean existsByEmail(String email);
    boolean existsById(Long id);

    // Suppression
    void deleteById(Long id);

    // Sauvegarde
    User save(User user);
}
