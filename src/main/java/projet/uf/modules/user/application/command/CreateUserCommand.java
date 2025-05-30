package projet.uf.modules.user.application.command;

import projet.uf.modules.user.domain.model.User;

public record CreateUserCommand(
        String email,
        String passwrd,
        String displayNme,
        boolean isAdmin
) {

    public static User toModel(CreateUserCommand command) {
        return new User(
                command.email(),
                command.passwrd(),
                command.displayNme(),
                command.isAdmin()
        );
    }

    public static User toModel(CreateUserCommand command, String encodedPassword) {
        return new User(
                command.email(),
                encodedPassword,
                command.displayNme(),
                command.isAdmin()
        );
    }
}
