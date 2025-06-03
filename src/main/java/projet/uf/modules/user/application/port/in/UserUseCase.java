package projet.uf.modules.user.application.port.in;

import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.user.application.dto.AUserDto;
import java.util.List;

public interface UserUseCase {
    AUserDto getById(Long id, OperatorUser operator);

    List<AUserDto> getAll(OperatorUser operator);

    void promoteUserToAdmin(Long userId, OperatorUser operator);

    void demoteUserFromAdmin(Long userId, OperatorUser operator);

    List<AUserDto> getAllAdminUsers(OperatorUser operator);
}
