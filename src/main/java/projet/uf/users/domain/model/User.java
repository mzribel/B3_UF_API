package projet.uf.users.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class User {
    private Long id;
    private String email;
    private String password;
    private String displayName;
    private boolean isAdmin = false;

    public User(String email, String password, String displayName, boolean isAdmin) {
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.isAdmin = isAdmin;
    }
    public User(String email, String password, String displayName) {
        this(email, password, displayName, false);
    }
}
