package projet.uf.modules.user.application.dto;

import lombok.Getter;
import projet.uf.modules.user.domain.model.User;

@Getter
public class UserDto extends AUserDto {
    private final String email;

    public UserDto(Long id, String email, String displayName) {
        super(id, displayName);
        this.email = email;
    }
    public static UserDto fromUser(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getDisplayName());
    }
}
