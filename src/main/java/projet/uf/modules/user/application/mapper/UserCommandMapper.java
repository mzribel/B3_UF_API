package projet.uf.modules.user.application.mapper;

import projet.uf.modules.user.application.ports.in.CreateUserCommand;
import projet.uf.modules.user.domain.model.User;

public class UserCommandMapper {
    public static User fromCreateCommand(CreateUserCommand command) {
        return new User(
                command.getEmail(),
                command.getPassword(),
                command.getDisplayName(),
                command.isAdmin()
        );
    }
    public static User fromCreateCommand(CreateUserCommand command, String encodedPassword) {
        return new User(
                command.getEmail(),
                encodedPassword,
                command.getDisplayName(),
                command.isAdmin()
        );
    }
}
