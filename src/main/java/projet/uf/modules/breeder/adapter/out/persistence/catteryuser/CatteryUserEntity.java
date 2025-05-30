package projet.uf.modules.breeder.adapter.out.persistence.catteryuser;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projet.uf.modules.breeder.adapter.out.persistence.cattery.CatteryEntity;
import projet.uf.modules.breeder.domain.model.CatteryUser;
import projet.uf.modules.user.adapter.out.persistence.UserEntity;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cattery_users")
public class CatteryUserEntity {
    @EmbeddedId
    private CatteryUserId id;

    @ManyToOne
    @MapsId("catteryId") // lie à l'attribut de id.catteryId
    @JoinColumn(name = "cattery_id")
    private CatteryEntity cattery;

    @ManyToOne
    @MapsId("userId") // lie à l'attribut de id.userId
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public CatteryUserEntity(CatteryEntity cattery, UserEntity user) {
        this.cattery = cattery;
        this.user = user;
        this.id = new CatteryUserId(cattery.getId(), user.getId());
    }
    public static CatteryUser toModel(CatteryUserEntity entity) {
        return new CatteryUser(
                entity.getCattery().getId(),
                entity.getUser().getId()
        );
    }

    public static CatteryUserEntity toEntity(CatteryEntity cattery, UserEntity user) {
        return new CatteryUserEntity(cattery, user);
    }

}
