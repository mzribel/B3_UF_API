package projet.uf.modules.user.application.port.in;

import projet.uf.modules.user.domain.model.User;

public interface CreateUserUseCase {
    User createUser(CreateUserCommand command);
}
