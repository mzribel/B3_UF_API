package projet.uf.modules.breeder.adapter.out.persistence.catteryuser;

import projet.uf.modules.breeder.domain.model.CatteryUser;
import projet.uf.modules.user.adapter.out.persistence.UserEntity;
import projet.uf.modules.breeder.adapter.out.persistence.cattery.CatteryEntity;

public class CatteryUserEntityMapper {

    public static CatteryUser toModel(CatteryUserEntity entity) {
        return new CatteryUser(
                entity.getCattery().getId(),
                entity.getUser().getId()
        );
    }

    public static CatteryUserEntity toEntity(CatteryUser model, CatteryEntity cattery, UserEntity user) {
        return new CatteryUserEntity(cattery, user);
    }
}
