package projet.uf.modules.user.application.port.in;

import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.user.application.dto.UserDto;
import java.util.List;

public interface UserUseCase {
    UserDto getById(Long id);
    List<UserDto> getAll(OperatorUser operator);
    UserDto createUser(CreateUserCommand command, OperatorUser operator);
}
