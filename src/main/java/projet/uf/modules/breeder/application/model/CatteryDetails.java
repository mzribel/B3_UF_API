package projet.uf.modules.breeder.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import projet.uf.modules.breeder.domain.model.Breeder;
import projet.uf.modules.user.application.dto.UserDto;
import projet.uf.modules.user.domain.model.User;

import java.util.List;

@AllArgsConstructor
@Getter
public class CatteryDetails {
    private Long id;
    private UserDto createdByUser;
    private Breeder linkedBreeder;
    private List<User> members;
}
