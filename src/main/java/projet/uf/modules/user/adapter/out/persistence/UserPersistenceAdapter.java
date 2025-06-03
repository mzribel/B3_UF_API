package projet.uf.modules.user.adapter.out.persistence;

import projet.uf.modules.user.application.port.out.UserPersistencePort;
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
        return jpaUserRepository.findById(id).map(UserEntity::toModel);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return jpaUserRepository.findByEmail(email).map(UserEntity::toModel);
    }

    @Override
    public List<User> getAll() {
        return jpaUserRepository.findAll()
                .stream()
                .map(UserEntity::toModel)
                .toList();
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaUserRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        jpaUserRepository.deleteById(id);
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = UserEntity.toEntity(user);
        UserEntity saved = jpaUserRepository.save(userEntity);
        return UserEntity.toModel(saved);
    }

    @Override
    public List<User> getAllAdminUsers() {
        return jpaUserRepository.findAllByAdmin(true)
                .stream()
                .map(UserEntity::toModel)
                .toList();
    }
}
