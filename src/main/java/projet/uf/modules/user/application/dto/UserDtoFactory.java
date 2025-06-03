package projet.uf.modules.user.application.dto;

import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.user.domain.model.User;

public class UserDtoFactory {
    public static AUserDto fromUser(User user, OperatorUser currentUser) {
        if (currentUser.isAdmin()) {
            return new AdminUserDto(
                    user.getId(),
                    user.getDisplayName(),
                    user.getEmail(),
                    user.isAdmin()
            );
        } else {
            return new UserDto(
                    user.getId(),
                    user.getEmail(),
                    user.getDisplayName()
            );
        }
    }
}
