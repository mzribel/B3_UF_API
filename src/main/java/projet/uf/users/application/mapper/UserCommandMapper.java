package projet.uf.users.application.mapper;

import projet.uf.users.application.ports.in.CreateUserCommand;
import projet.uf.users.domain.model.User;

public class UserCommandMapper {
    public static User fromCreateCommand(CreateUserCommand command) {
        return new User(
                command.getEmail(),
                command.getPassword(),
                command.getDisplayName(),
                command.isAdmin()
        );
    }
}
