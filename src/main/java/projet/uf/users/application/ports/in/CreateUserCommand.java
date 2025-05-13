package projet.uf.users.application.ports.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreateUserCommand {
    private String email;
    private String password;
    private String displayName;
    private boolean isAdmin;
}
