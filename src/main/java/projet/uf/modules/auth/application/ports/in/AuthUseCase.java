package projet.uf.modules.auth.application.ports.in;

import projet.uf.modules.auth.adapters.in.rest.dto.AuthenticatedUserDto;

public interface AuthUseCase {
    AuthenticatedUserDto register(RegisterCommand command);
    AuthenticatedUserDto login(LoginCommand command);
}
