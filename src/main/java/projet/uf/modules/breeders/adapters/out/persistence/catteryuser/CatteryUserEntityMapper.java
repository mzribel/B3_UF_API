package projet.uf.modules.breeders.adapters.out.persistence.catteryuser;

import projet.uf.modules.breeders.domain.model.CatteryUser;
import projet.uf.modules.user.adapters.out.persistence.UserEntity;
import projet.uf.modules.breeders.adapters.out.persistence.cattery.CatteryEntity;

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
