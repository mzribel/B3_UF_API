package projet.uf.modules.auth.application.ports.in;

import projet.uf.modules.user.domain.model.User;

public interface AuthUseCase {
    public User register(RegisterCommand command);
    public User login(LoginCommand command);
}
