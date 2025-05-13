package projet.uf.modules.user.adapters.out.persistence;

import projet.uf.modules.user.adapters.out.persistence.JpaUserRepository;
import projet.uf.modules.user.adapters.out.persistence.UserEntity;
import projet.uf.modules.user.adapters.out.persistence.UserEntityMapper;
import projet.uf.modules.user.application.ports.out.UserPersistencePort;
import projet.uf.modules.user.domain.model.User;

import java.util.List;
import java.util.Optional;

public class UserPersistenceAdapter implements UserPersistencePort {
    private final JpaUserRepository jpaUserRepository;

    public UserPersistenceAdapter(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Optional<User> getById(Long id) {
        return jpaUserRepository.findById(id).map(UserEntityMapper::toModel);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return jpaUserRepository.findByEmail(email).map(UserEntityMapper::toModel);
    }

    @Override
    public List<User> getAll() {
        return jpaUserRepository.findAll()
                .stream()
                .map(UserEntityMapper::toModel)
                .toList();
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    @Override
    public void deleteById(Long id) {
        // TODO : condition de suppression (ex : existence préalable)
        jpaUserRepository.deleteById(id);
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = UserEntityMapper.toEntity(user);
        UserEntity saved = jpaUserRepository.save(userEntity);
        return UserEntityMapper.toModel(saved);
    }
}
