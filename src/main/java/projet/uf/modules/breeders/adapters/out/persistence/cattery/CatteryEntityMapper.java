package projet.uf.modules.breeders.adapters.out.persistence.cattery;

import projet.uf.modules.breeders.domain.model.Cattery;

public class CatteryEntityMapper {
    public static Cattery toModel(CatteryEntity entity) {
        return new Cattery(
            entity.getId(),
            entity.getCreatedByUserId(),
            entity.getLinkedToBreederId()
        );
    }

    public static CatteryEntity toEntity(Cattery model) {
        return new CatteryEntity(
            model.getId(),
            model.getCreatedByUserId(),
            model.getLinkedToBreederId()
        );
    }
}
