package projet.uf.users.adapters.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String displayName;

    @Column
    private boolean isAdmin;

    public UserEntity(String email, String password, String displayName, boolean isAdmin) {
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.isAdmin = isAdmin;
    }

    public UserEntity(String email, String password, String displayName) {
        this(email, password, displayName, false);
    }

}
