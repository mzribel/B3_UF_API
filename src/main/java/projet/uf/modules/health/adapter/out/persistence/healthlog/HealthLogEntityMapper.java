package projet.uf.modules.health.adapter.out.persistence.healthlog;

import projet.uf.modules.health.domain.model.HealthLog;

public class HealthLogEntityMapper {
    public static HealthLog toModel(HealthLogEntity entity) {
        return HealthLog.builder()
                .id(entity.getId())
                .catId(entity.getCatId())
                .weightInGrams(entity.getWeightInGrams())
                .temperatureInCelsius(entity.getTemperatureInCelsius())
                .appetite(entity.getAppetite())
                .hydratation(entity.getHydratation())
                .behavior(entity.getBehavior())
                .stoolQuality(entity.getStoolQuality())
                .urineObservations(entity.getUrineObservations())
                .notes(entity.getNotes())
                .date(entity.getDate())
                .build();
    }

    public static HealthLogEntity toEntity(HealthLog model) {
        return new HealthLogEntity(
                model.getId(),
                model.getCatId(),
                model.getWeightInGrams(),
                model.getTemperatureInCelsius(),
                model.getAppetite(),
                model.getHydratation(),
                model.getBehavior(),
                model.getStoolQuality(),
                model.getUrineObservations(),
                model.getNotes(),
                model.getDate()
        );
    }
}