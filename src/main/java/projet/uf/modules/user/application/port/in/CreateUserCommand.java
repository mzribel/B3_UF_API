package projet.uf.modules.user.application.port.in;

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
