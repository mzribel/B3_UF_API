package projet.uf.modules.user.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projet.uf.modules.user.domain.model.User;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String password;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "is_admin")
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

    public static User toModel(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getDisplayName(),
                entity.isAdmin()
        );
    }

    public static UserEntity toEntity(User model) {
        return new UserEntity(
                model.getId(),
                model.getEmail(),
                model.getPassword(),
                model.getDisplayName(),
                model.isAdmin()
        );
    }
}
