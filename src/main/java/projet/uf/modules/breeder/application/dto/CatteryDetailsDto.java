package projet.uf.modules.breeder.application.dto;

import projet.uf.modules.breeder.domain.model.Breeder;
import projet.uf.modules.user.application.dto.UserDto;

import java.util.List;

public record CatteryDetailsDto(
        Long id,
        UserDto createdByUser,
        Breeder linkedBreeder,
        List<UserDto> members
) {}
