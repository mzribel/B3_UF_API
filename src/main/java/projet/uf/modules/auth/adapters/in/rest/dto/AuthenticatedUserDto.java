package projet.uf.modules.auth.adapters.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import projet.uf.modules.user.application.dto.UserDto;

public record AuthenticatedUserDto(
        @JsonProperty("user")
        UserDto userDto,
        String token
) {}
