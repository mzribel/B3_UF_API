package projet.uf.modules.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class AUserDto {
    private Long id;
    private String displayName;
}
