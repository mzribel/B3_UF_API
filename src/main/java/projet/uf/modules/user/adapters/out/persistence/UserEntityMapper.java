package projet.uf.modules.user.adapters.out.persistence;

import projet.uf.modules.user.domain.model.User;

public class UserEntityMapper {
    public static User toModel(UserEntity entity) {
        return new User(
            entity.getId(),
            entity.getEmail(),
            entity.getPassword(),
            entity.getDisplayName(),
            entity.isAdmin()
        );
    }

    public static UserEntity toEntity(User model) {
        return new UserEntity(
            model.getId(),
            model.getEmail(),
            model.getPassword(),
            model.getDisplayName(),
            model.isAdmin()
        );
    }
}
