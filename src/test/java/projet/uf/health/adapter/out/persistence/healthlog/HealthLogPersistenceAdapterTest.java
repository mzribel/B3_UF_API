package projet.uf.health.adapter.out.persistence.healthlog;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projet.uf.modules.health.adapter.out.persistence.healthlog.HealthLogEntity;
import projet.uf.modules.health.adapter.out.persistence.healthlog.HealthLogEntityMapper;
import projet.uf.modules.health.adapter.out.persistence.healthlog.HealthLogPersistenceAdapter;
import projet.uf.modules.health.adapter.out.persistence.healthlog.JpaHealthLogRepository;
import projet.uf.modules.health.domain.model.HealthLog;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HealthLogPersistenceAdapterTest {

    @Mock
    private JpaHealthLogRepository jpaHealthLogRepository;

    @InjectMocks
    private HealthLogPersistenceAdapter healthLogPersistenceAdapter;

    @Test
    void getById_shouldReturnHealthLog_whenExists() {
        // Arrange
        Long id = 1L;
        HealthLogEntity entity = new HealthLogEntity(
                id,
                1L,
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(38.5),
                "Good",
                "Normal",
                "Active",
                "Normal",
                "Normal",
                "Test notes",
                LocalDateTime.now()
        );
        
        when(jpaHealthLogRepository.findById(id)).thenReturn(Optional.of(entity));

        // Act
        Optional<HealthLog> result = healthLogPersistenceAdapter.getById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        assertEquals(entity.getCatId(), result.get().getCatId());
        assertEquals(entity.getWeightInGrams(), result.get().getWeightInGrams());
        assertEquals(entity.getTemperatureInCelsius(), result.get().getTemperatureInCelsius());
        assertEquals(entity.getAppetite(), result.get().getAppetite());
        assertEquals(entity.getHydratation(), result.get().getHydratation());
        assertEquals(entity.getBehavior(), result.get().getBehavior());
        assertEquals(entity.getStoolQuality(), result.get().getStoolQuality());
        assertEquals(entity.getUrineObservations(), result.get().getUrineObservations());
        assertEquals(entity.getNotes(), result.get().getNotes());
        assertEquals(entity.getDate(), result.get().getDate());
        
        verify(jpaHealthLogRepository).findById(id);
    }

    @Test
    void getById_shouldReturnEmpty_whenNotExists() {
        // Arrange
        Long id = 1L;
        when(jpaHealthLogRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<HealthLog> result = healthLogPersistenceAdapter.getById(id);

        // Assert
        assertTrue(result.isEmpty());
        verify(jpaHealthLogRepository).findById(id);
    }

    @Test
    void getAll_shouldReturnAllHealthLogs() {
        // Arrange
        HealthLogEntity entity1 = new HealthLogEntity(
                1L,
                1L,
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(38.5),
                "Good",
                "Normal",
                "Active",
                "Normal",
                "Normal",
                "Test notes 1",
                LocalDateTime.now()
        );
        
        HealthLogEntity entity2 = new HealthLogEntity(
                2L,
                2L,
                BigDecimal.valueOf(1200),
                BigDecimal.valueOf(38.2),
                "Good",
                "Normal",
                "Active",
                "Normal",
                "Normal",
                "Test notes 2",
                LocalDateTime.now()
        );
        
        List<HealthLogEntity> entities = Arrays.asList(entity1, entity2);
        when(jpaHealthLogRepository.findAll()).thenReturn(entities);

        // Act
        List<HealthLog> result = healthLogPersistenceAdapter.getAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals(entity1.getId(), result.get(0).getId());
        assertEquals(entity2.getId(), result.get(1).getId());
        
        verify(jpaHealthLogRepository).findAll();
    }

    @Test
    void getByCatId_shouldReturnHealthLogsForCat() {
        // Arrange
        Long catId = 1L;
        HealthLogEntity entity1 = new HealthLogEntity(
                1L,
                catId,
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(38.5),
                "Good",
                "Normal",
                "Active",
                "Normal",
                "Normal",
                "Test notes 1",
                LocalDateTime.now()
        );
        
        HealthLogEntity entity2 = new HealthLogEntity(
                2L,
                catId,
                BigDecimal.valueOf(1200),
                BigDecimal.valueOf(38.2),
                "Good",
                "Normal",
                "Active",
                "Normal",
                "Normal",
                "Test notes 2",
                LocalDateTime.now()
        );
        
        List<HealthLogEntity> entities = Arrays.asList(entity1, entity2);
        when(jpaHealthLogRepository.findByCatId(catId)).thenReturn(entities);

        // Act
        List<HealthLog> result = healthLogPersistenceAdapter.getByCatId(catId);

        // Assert
        assertEquals(2, result.size());
        assertEquals(catId, result.get(0).getCatId());
        assertEquals(catId, result.get(1).getCatId());
        
        verify(jpaHealthLogRepository).findByCatId(catId);
    }

    @Test
    void save_shouldSaveAndReturnHealthLog() {
        // Arrange
        HealthLog healthLog = new HealthLog(
                1L,
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(38.5),
                "Good",
                "Normal",
                "Active",
                "Normal",
                "Normal",
                "Test notes"
        );
        
        HealthLogEntity entity = HealthLogEntityMapper.toEntity(healthLog);
        when(jpaHealthLogRepository.save(any(HealthLogEntity.class))).thenReturn(entity);

        // Act
        HealthLog result = healthLogPersistenceAdapter.save(healthLog);

        // Assert
        assertEquals(healthLog.getCatId(), result.getCatId());
        assertEquals(healthLog.getWeightInGrams(), result.getWeightInGrams());
        assertEquals(healthLog.getTemperatureInCelsius(), result.getTemperatureInCelsius());
        assertEquals(healthLog.getAppetite(), result.getAppetite());
        assertEquals(healthLog.getHydratation(), result.getHydratation());
        assertEquals(healthLog.getBehavior(), result.getBehavior());
        assertEquals(healthLog.getStoolQuality(), result.getStoolQuality());
        assertEquals(healthLog.getUrineObservations(), result.getUrineObservations());
        assertEquals(healthLog.getNotes(), result.getNotes());
        
        verify(jpaHealthLogRepository).save(any(HealthLogEntity.class));
    }

    @Test
    void deleteById_shouldCallDeleteOnRepository() {
        // Arrange
        Long id = 1L;

        // Act
        healthLogPersistenceAdapter.deleteById(id);

        // Assert
        verify(jpaHealthLogRepository).deleteById(id);
    }
}