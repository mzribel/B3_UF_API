package projet.uf.modules.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import projet.uf.modules.user.domain.model.User;

@Getter
@AllArgsConstructor
public class UserDto {
    Long id;
    String email;
    String displayName;
    
    public static UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getDisplayName());
    }
}
