package projet.uf.modules.user.adapter.in.rest.dto;

import projet.uf.modules.user.domain.model.User;

public class UserDtoMapper {
    public static UserDto toDto (User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getDisplayName(), user.isAdmin());
    }
}
