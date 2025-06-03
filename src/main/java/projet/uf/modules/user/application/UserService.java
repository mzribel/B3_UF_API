package projet.uf.modules.user.application;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.user.application.dto.AUserDto;
import projet.uf.modules.user.application.dto.UserDtoFactory;
import projet.uf.modules.user.application.port.in.UserUseCase;
import projet.uf.modules.user.application.port.out.UserPersistencePort;
import projet.uf.modules.user.domain.model.User;

import java.util.List;

@AllArgsConstructor
public class UserService implements UserUseCase {
    private final UserPersistencePort userPersistencePort;

    @Override
    public AUserDto getById(Long id, OperatorUser operator) {
        return userPersistencePort.getById(id)
                .map(user -> UserDtoFactory.fromUser(user, operator))
                .orElseThrow(() -> new ApiException("Utilisateur introuvable", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<AUserDto> getAll(OperatorUser operator) {
        if (!operator.isAdmin()) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }

        return userPersistencePort.getAll()
                .stream().map(user -> UserDtoFactory.fromUser(user, operator))
                .toList();
    }

    // TODO : super admin pour éviter de se faire kick mdr
    @Override
    public void promoteUserToAdmin(Long userId, OperatorUser operator) {
        if (!operator.isAdmin()) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }
        User user = userPersistencePort.getById(userId)
                .orElseThrow(() -> new ApiException("Utilisateur introuvable", HttpStatus.NOT_FOUND));

        if (user.isAdmin()) {
            throw new ApiException("L'utilisateur est déjà administrateur", HttpStatus.CONFLICT);
        }

        user.setAdmin(true);
        userPersistencePort.save(user);
    }

    @Override
    public void demoteUserFromAdmin(Long userId, OperatorUser operator) {
        if (!operator.isAdmin()) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }
        User user = userPersistencePort.getById(userId)
                .orElseThrow(() -> new ApiException("Utilisateur introuvable", HttpStatus.NOT_FOUND));

        if (!user.isAdmin()) {
            throw new ApiException("L'utilisateur n'est pas administrateur", HttpStatus.BAD_REQUEST);
        }

        if (userPersistencePort.getAllAdminUsers().size() <= 1) {
            throw new ApiException("Impossible de supprimer le seul administrateur", HttpStatus.BAD_REQUEST);
        }

        user.setAdmin(false);
        userPersistencePort.save(user);
    }

    @Override
    public List<AUserDto> getAllAdminUsers(OperatorUser operator) {
        if (!operator.isAdmin()) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }
        return userPersistencePort.getAllAdminUsers()
                .stream().map(user->UserDtoFactory.fromUser(user, operator))
                .toList();
    }
}
