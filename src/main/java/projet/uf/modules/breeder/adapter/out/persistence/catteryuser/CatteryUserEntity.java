package projet.uf.modules.breeder.adapter.out.persistence.catteryuser;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projet.uf.modules.breeder.adapter.out.persistence.cattery.CatteryEntity;
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
}
