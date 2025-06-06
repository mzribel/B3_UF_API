package projet.uf.modules.health.adapter.out.persistence.healthlog;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projet.uf.modules.health.adapter.out.persistence.healthlog.HealthLogEntity;
import projet.uf.modules.health.adapter.out.persistence.healthlog.HealthLogPersistenceAdapter;
import projet.uf.modules.health.adapter.out.persistence.healthlog.JpaHealthLogRepository;
import projet.uf.modules.health.domain.model.HealthLog;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HealthLogPersistenceAdapterTest {

    @Mock
    private JpaHealthLogRepository jpaHealthLogRepository;

    @InjectMocks
    private HealthLogPersistenceAdapter healthLogPersistenceAdapter;

    @Test
    void getById_shouldReturnHealthLog_whenExists() {
        Long id = 1L;
        HealthLogEntity entity = new HealthLogEntity(
                id, 1L,
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(38.5),
                "Good", "Normal", "Active", "Normal", "Normal",
                "Test notes", LocalDateTime.of(2024, 1, 1, 12, 0)
        );
        when(jpaHealthLogRepository.findById(id)).thenReturn(Optional.of(entity));

        Optional<HealthLog> result = healthLogPersistenceAdapter.getById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        assertEquals(entity.getNotes(), result.get().getNotes());
        verify(jpaHealthLogRepository).findById(id);
    }

    @Test
    void getById_shouldReturnEmpty_whenNotExists() {
        Long id = 99L;
        when(jpaHealthLogRepository.findById(id)).thenReturn(Optional.empty());

        Optional<HealthLog> result = healthLogPersistenceAdapter.getById(id);

        assertTrue(result.isEmpty());
        verify(jpaHealthLogRepository).findById(id);
    }

    @Test
    void getAll_shouldReturnHealthLogs() {
        List<HealthLogEntity> entities = Arrays.asList(
                new HealthLogEntity(1L, 1L, BigDecimal.valueOf(1000), BigDecimal.valueOf(38.5),
                        "Good", "Normal", "Active", "Normal", "Normal", "Note 1",
                        LocalDateTime.of(2024, 1, 1, 12, 0)),
                new HealthLogEntity(2L, 2L, BigDecimal.valueOf(1200), BigDecimal.valueOf(38.3),
                        "Good", "Normal", "Active", "Normal", "Normal", "Note 2",
                        LocalDateTime.of(2024, 1, 2, 12, 0))
        );
        when(jpaHealthLogRepository.findAll()).thenReturn(entities);

        List<HealthLog> result = healthLogPersistenceAdapter.getAll();

        assertEquals(2, result.size());
        verify(jpaHealthLogRepository).findAll();
    }

    @Test
    void getByCatId_shouldReturnHealthLogsForCat() {
        Long catId = 42L;
        List<HealthLogEntity> entities = List.of(
                new HealthLogEntity(1L, catId, BigDecimal.valueOf(900), BigDecimal.valueOf(38),
                        "Low", "Normal", "Calm", "Normal", "Normal", "Notes",
                        LocalDateTime.of(2024, 1, 3, 12, 0))
        );
        when(jpaHealthLogRepository.findByCatId(catId)).thenReturn(entities);

        List<HealthLog> result = healthLogPersistenceAdapter.getByCatId(catId);

        assertEquals(1, result.size());
        assertEquals(catId, result.getFirst().getCatId());
        verify(jpaHealthLogRepository).findByCatId(catId);
    }

    @Test
    void save_shouldReturnSavedHealthLog() {
        HealthLog model = new HealthLog(
                1L, BigDecimal.valueOf(950), BigDecimal.valueOf(38.7), "Good",
                "Normal", "Playful", "Normal", "Normal", "Recovered", LocalDateTime.of(2024, 1, 4, 12, 0)
        );
        model.setId(99L);

        HealthLogEntity savedEntity = HealthLogEntity.toEntity(model);
        when(jpaHealthLogRepository.save(any())).thenReturn(savedEntity);

        HealthLog result = healthLogPersistenceAdapter.save(model);

        assertEquals(model.getWeightInGrams(), result.getWeightInGrams());
        assertEquals(model.getTemperatureInCelsius(), result.getTemperatureInCelsius());
        assertEquals(model.getNotes(), result.getNotes());
        verify(jpaHealthLogRepository).save(any());
    }

    @Test
    void deleteById_shouldCallRepository() {
        Long id = 5L;

        healthLogPersistenceAdapter.deleteById(id);

        verify(jpaHealthLogRepository).deleteById(id);
    }
}
