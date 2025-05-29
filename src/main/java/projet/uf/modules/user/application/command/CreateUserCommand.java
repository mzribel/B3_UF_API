package projet.uf.modules.user.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import projet.uf.modules.user.domain.model.User;

@AllArgsConstructor
@Getter
@Setter
public class CreateUserCommand {
    private String email;
    private String password;
    private String displayName;
    private boolean isAdmin;

    public static User toModel(CreateUserCommand command) {
        return new User(
                command.getEmail(),
                command.getPassword(),
                command.getDisplayName(),
                command.isAdmin()
        );
    }
    public static User toModel(CreateUserCommand command, String encodedPassword) {
        return new User(
                command.getEmail(),
                encodedPassword,
                command.getDisplayName(),
                command.isAdmin()
        );
    }
}
