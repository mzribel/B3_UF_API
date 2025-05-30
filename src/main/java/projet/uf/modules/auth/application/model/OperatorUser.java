package projet.uf.modules.auth.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OperatorUser {
    private Long id;
    private boolean isAdmin;

    public static OperatorUser fromCurrentUser(CurrentUser currentUser) {
        return new OperatorUser(currentUser.id(), currentUser.isAdmin());
    }
}
