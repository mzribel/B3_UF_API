package projet.uf.modules.user.application.dto;

import lombok.Getter;

@Getter
public class AdminUserDto extends AUserDto {
    private final String email;
    private final boolean isAdmin;

    public AdminUserDto(Long id, String email, String displayName, boolean isAdmin) {
        super(id, displayName);
        this.email = email;
        this.isAdmin = isAdmin;
    }
}
