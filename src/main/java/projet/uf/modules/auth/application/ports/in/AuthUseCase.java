package projet.uf.modules.auth.application.ports.in;

import projet.uf.modules.user.domain.model.User;

public interface AuthUseCase {
    User register(RegisterCommand command);
    User login(LoginCommand command);
}
