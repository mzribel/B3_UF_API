package projet.uf.modules.health.adapter.out.persistence.kittenhealthlog;

import projet.uf.modules.health.domain.model.KittenHealthLog;

public class KittenHealthLogEntityMapper {
    public static KittenHealthLog toModel(KittenHealthLogEntity entity) {
        return KittenHealthLog.builder()
                .id(entity.getId())
                .healthLogId(entity.getHealthLogId())
                .openEyesDate(entity.getOpenEyesDate())
                .firstWakDate(entity.getFirstWakDate())
                .build();
    }

    public static KittenHealthLogEntity toEntity(KittenHealthLog model) {
        return new KittenHealthLogEntity(
                model.getId(),
                model.getHealthLogId(),
                model.getOpenEyesDate(),
                model.getFirstWakDate()
        );
    }
}