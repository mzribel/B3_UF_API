package projet.uf.modules.breeders.adapters.out.persistence.breeder;

import projet.uf.modules.breeders.domain.model.Breeder;

public class BreederEntityMapper {
    public static Breeder toModel(BreederEntity entity) {
        return new Breeder(
            entity.getId(),
            entity.getName(),
            entity.getSiret(),
            entity.getAffix(),
            entity.isAffixPrefix(),
            entity.getOwnerId(),
            entity.getAddressId(),
            entity.getCreatedByCatteryId(),
            entity.isActive(),
            entity.isDerogatory()
        );
    }

    public static BreederEntity toEntity(Breeder model) {
        return new BreederEntity(
                model.getId(),
                model.getName(),
                model.getSiret(),
                model.getAffix(),
                model.isAffixPrefix(),
                model.getOwnerId(),
                model.getAddressId(),
                model.getCreatedByCatteryId(),
                model.isActive(),
                model.isDerogatory()
        );
    }
}
