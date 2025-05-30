package projet.uf.modules.user.application;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.auth.exception.UserAlreadyExistsException;
import projet.uf.modules.user.application.dto.UserDto;
import projet.uf.modules.user.application.command.CreateUserCommand;
import projet.uf.modules.user.application.port.in.UserUseCase;
import projet.uf.modules.user.application.port.out.UserPersistencePort;
import projet.uf.modules.user.domain.model.User;

import java.util.List;

@AllArgsConstructor
public class UserService implements UserUseCase {
    private final UserPersistencePort userPersistencePort;

    @Override
    public UserDto createUser(CreateUserCommand command, OperatorUser operatorUser) {
        if (!operatorUser.isAdmin()) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }

        if (userPersistencePort.existsByEmail(command.email())) {
            throw new UserAlreadyExistsException("Un utilisateur avec cette adresse email existe déjà", HttpStatus.CONFLICT);
        }

        User user = CreateUserCommand.toModel(command);
        return UserDto.toDto(userPersistencePort.save(user));
    }

    @Override
    public UserDto getById(Long id) {
        return userPersistencePort.getById(id)
                .map(UserDto::toDto)
                .orElseThrow(() -> new ApiException("Utilisateur introuvable", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<UserDto> getAll(OperatorUser operator) {
        if (!operator.isAdmin()) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }

        return userPersistencePort.getAll()
                .stream().map(UserDto::toDto)
                .toList();
    }
}
