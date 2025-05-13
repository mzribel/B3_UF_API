package projet.uf.users.application.ports.out;

import projet.uf.users.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserPersistencePort {
    // Récupération
    Optional<User> getById(Long id);
    Optional<User> getByEmail(String email);
    List<User> getAll();

    // Vérification
    boolean existsByEmail(String email);

    // Suppression
    void deleteById(Long id);

    // Sauvegarde
    User save(User user);
}
