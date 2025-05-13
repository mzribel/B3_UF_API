package projet.uf.modules.user.application.ports.in;

import projet.uf.modules.user.application.ports.in.CreateUserCommand;
import projet.uf.modules.user.domain.model.User;

public interface CreateUserUseCase {
    User createUser(CreateUserCommand command);
}
