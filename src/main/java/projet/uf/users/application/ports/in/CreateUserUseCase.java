package projet.uf.users.application.ports.in;

import projet.uf.users.domain.model.User;

public interface CreateUserUseCase {
    User createUser(CreateUserCommand command);
}
