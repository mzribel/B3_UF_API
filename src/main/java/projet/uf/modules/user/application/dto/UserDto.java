package projet.uf.modules.user.application.dto;

import projet.uf.modules.user.domain.model.User;

public record UserDto(
        Long id,
        String email,
        String displayName
) {
    public static UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getDisplayName());
    }
}
