package projet.uf.modules.auth.adapters.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import projet.uf.modules.user.application.dto.UserDto;

@Getter
@AllArgsConstructor
public class AuthenticatedUserDto {
    @JsonProperty("user")
    private UserDto userDto;

    private String token;
}
