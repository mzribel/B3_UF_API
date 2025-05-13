package projet.uf.modules.user.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import projet.uf.modules.user.adapters.out.persistence.UserEntity;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
