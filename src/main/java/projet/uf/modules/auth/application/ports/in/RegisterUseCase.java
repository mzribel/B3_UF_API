package projet.uf.modules.auth.application.ports.in;

import projet.uf.modules.user.application.ports.in.CreateUserCommand;
import projet.uf.modules.user.domain.model.User;

public interface RegisterUseCase {
    public User register(CreateUserCommand command);
}
