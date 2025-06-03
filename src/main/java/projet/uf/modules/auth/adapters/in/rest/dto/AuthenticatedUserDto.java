package projet.uf.modules.auth.adapters.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import projet.uf.modules.user.application.dto.AUserDto;

public record AuthenticatedUserDto(
        @JsonProperty("user")
        AUserDto userDto,
        String token
) {}
