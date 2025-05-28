package projet.uf.modules.health.adapter.out.persistence.gestationhealthlog;

import projet.uf.modules.health.domain.model.GestationHealthLog;

public class GestationHealthLogEntityMapper {
    public static GestationHealthLog toModel(GestationHealthLogEntity entity) {
        return GestationHealthLog.builder()
                .id(entity.getId())
                .gestationId(entity.getGestationId())
                .healthLogId(entity.getHealthLogId())
                .weight(entity.getWeight())
                .temperature(entity.getTemperature())
                .behavior(entity.getBehavior())
                .notes(entity.getNotes())
                .mammaryObservations(entity.getMammaryObservations())
                .kittenMovement(entity.getKittenMovement())
                .build();
    }

    public static GestationHealthLogEntity toEntity(GestationHealthLog model) {
        return new GestationHealthLogEntity(
                model.getId(),
                model.getGestationId(),
                model.getHealthLogId(),
                model.getWeight(),
                model.getTemperature(),
                model.getBehavior(),
                model.getNotes(),
                model.getMammaryObservations(),
                model.getKittenMovement()
        );
    }
}