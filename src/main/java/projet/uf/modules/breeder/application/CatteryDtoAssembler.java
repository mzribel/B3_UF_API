package projet.uf.modules.breeder.application;

import lombok.AllArgsConstructor;
import projet.uf.modules.breeder.application.dto.CatteryDetailsDto;
import projet.uf.modules.breeder.application.port.out.BreederPersistencePort;
import projet.uf.modules.breeder.application.port.out.CatteryUserPersistencePort;
import projet.uf.modules.breeder.domain.model.Breeder;
import projet.uf.modules.breeder.domain.model.Cattery;
import projet.uf.modules.user.application.dto.UserDto;
import projet.uf.modules.user.application.port.out.UserPersistencePort;
import projet.uf.modules.user.domain.model.User;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class CatteryDtoAssembler {
    private final UserPersistencePort userPersistencePort;
    private final BreederPersistencePort breederPersistencePort;
    private final CatteryUserPersistencePort catteryUserPersistencePort;

    public CatteryDetailsDto toDetailsDto(Cattery cattery) {
        User createdBy = userPersistencePort.getById(cattery.getCreatedByUserId())
                .orElse(new User(cattery.getCreatedByUserId())); // ou exception

        // Membres (filtre les absents si user supprimé par exemple)
        List<UserDto> members = catteryUserPersistencePort.getByCatteryId(cattery.getId()).stream()
                .map(cu -> userPersistencePort.getById(cu.getUserId()))
                .flatMap(Optional::stream)
                .map(UserDto::fromUser)
                .toList();

        Breeder breeder =
                cattery.getLinkedToBreederId() != null
                        ? breederPersistencePort.getById(cattery.getLinkedToBreederId()).orElse(null)
                        : null;

        return new CatteryDetailsDto(
                cattery.getId(),
                UserDto.fromUser(createdBy),
                breeder,
                members
        );
    }
}
