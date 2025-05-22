package projet.uf.modules.auth.application.ports.in;

import projet.uf.modules.user.domain.model.User;

public class AuthCommandMapper {
    public static User fromCreateCommand(RegisterCommand command) {
        return new User(
                command.email(),
                command.password(),
                command.displayName(),
                command.isAdmin()
        );
    }
    public static User fromCreateCommand(RegisterCommand command, String encodedPassword) {
        return new User(
                command.email(),
                encodedPassword,
                command.displayName(),
                command.isAdmin()
        );
    }
}
