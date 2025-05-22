package projet.uf.modules.auth.application.ports.in;

import projet.uf.modules.user.application.ports.in.CreateUserCommand;
import projet.uf.modules.user.domain.model.User;

public interface AuthService {
    public User register(RegisterCommand command);
    public String login(LoginCommand command);
}
