package projet.uf.health.adapter.out.persistence.gestationhealthlog;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projet.uf.modules.health.adapter.out.persistence.gestationhealthlog.GestationHealthLogEntity;
import projet.uf.modules.health.adapter.out.persistence.gestationhealthlog.GestationHealthLogEntityMapper;
import projet.uf.modules.health.adapter.out.persistence.gestationhealthlog.GestationHealthLogPersistenceAdapter;
import projet.uf.modules.health.adapter.out.persistence.gestationhealthlog.JpaGestationHealthLogRepository;
import projet.uf.modules.health.domain.model.GestationHealthLog;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GestationHealthLogPersistenceAdapterTest {

    @Mock
    private JpaGestationHealthLogRepository jpaGestationHealthLogRepository;

    @InjectMocks
    private GestationHealthLogPersistenceAdapter gestationHealthLogPersistenceAdapter;

    @Test
    void getById_shouldReturnGestationHealthLog_whenExists() {
        // Arrange
        Long id = 1L;
        GestationHealthLogEntity entity = new GestationHealthLogEntity(
                id,
                1L,
                2L,
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(38.5),
                "Normal",
                "Test notes",
                "Normal",
                true
        );
        
        when(jpaGestationHealthLogRepository.findById(id)).thenReturn(Optional.of(entity));

        // Act
        Optional<GestationHealthLog> result = gestationHealthLogPersistenceAdapter.getById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        assertEquals(entity.getGestationId(), result.get().getGestationId());
        assertEquals(entity.getHealthLogId(), result.get().getHealthLogId());
        assertEquals(entity.getWeight(), result.get().getWeight());
        assertEquals(entity.getTemperature(), result.get().getTemperature());
        assertEquals(entity.getBehavior(), result.get().getBehavior());
        assertEquals(entity.getNotes(), result.get().getNotes());
        assertEquals(entity.getMammaryObservations(), result.get().getMammaryObservations());
        assertEquals(entity.getKittenMovement(), result.get().getKittenMovement());
        
        verify(jpaGestationHealthLogRepository).findById(id);
    }

    @Test
    void getById_shouldReturnEmpty_whenNotExists() {
        // Arrange
        Long id = 1L;
        when(jpaGestationHealthLogRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<GestationHealthLog> result = gestationHealthLogPersistenceAdapter.getById(id);

        // Assert
        assertTrue(result.isEmpty());
        verify(jpaGestationHealthLogRepository).findById(id);
    }

    @Test
    void getByGestationId_shouldReturnGestationHealthLogs() {
        // Arrange
        Long gestationId = 1L;
        GestationHealthLogEntity entity1 = new GestationHealthLogEntity(
                1L,
                gestationId,
                2L,
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(38.5),
                "Normal",
                "Test notes 1",
                "Normal",
                true
        );
        
        GestationHealthLogEntity entity2 = new GestationHealthLogEntity(
                2L,
                gestationId,
                3L,
                BigDecimal.valueOf(1100),
                BigDecimal.valueOf(38.2),
                "Normal",
                "Test notes 2",
                "Normal",
                true
        );
        
        List<GestationHealthLogEntity> entities = Arrays.asList(entity1, entity2);
        when(jpaGestationHealthLogRepository.findByGestationId(gestationId)).thenReturn(entities);

        // Act
        List<GestationHealthLog> result = gestationHealthLogPersistenceAdapter.getByGestationId(gestationId);

        // Assert
        assertEquals(2, result.size());
        assertEquals(gestationId, result.get(0).getGestationId());
        assertEquals(gestationId, result.get(1).getGestationId());
        
        verify(jpaGestationHealthLogRepository).findByGestationId(gestationId);
    }

    @Test
    void getByHealthLogId_shouldReturnGestationHealthLog_whenExists() {
        // Arrange
        Long healthLogId = 1L;
        GestationHealthLogEntity entity = new GestationHealthLogEntity(
                1L,
                2L,
                healthLogId,
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(38.5),
                "Normal",
                "Test notes",
                "Normal",
                true
        );
        
        when(jpaGestationHealthLogRepository.findByHealthLogId(healthLogId)).thenReturn(Optional.of(entity));

        // Act
        Optional<GestationHealthLog> result = gestationHealthLogPersistenceAdapter.getByHealthLogId(healthLogId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(healthLogId, result.get().getHealthLogId());
        assertEquals(entity.getGestationId(), result.get().getGestationId());
        assertEquals(entity.getWeight(), result.get().getWeight());
        assertEquals(entity.getTemperature(), result.get().getTemperature());
        assertEquals(entity.getBehavior(), result.get().getBehavior());
        assertEquals(entity.getNotes(), result.get().getNotes());
        assertEquals(entity.getMammaryObservations(), result.get().getMammaryObservations());
        assertEquals(entity.getKittenMovement(), result.get().getKittenMovement());
        
        verify(jpaGestationHealthLogRepository).findByHealthLogId(healthLogId);
    }

    @Test
    void getByHealthLogId_shouldReturnEmpty_whenNotExists() {
        // Arrange
        Long healthLogId = 1L;
        when(jpaGestationHealthLogRepository.findByHealthLogId(healthLogId)).thenReturn(Optional.empty());

        // Act
        Optional<GestationHealthLog> result = gestationHealthLogPersistenceAdapter.getByHealthLogId(healthLogId);

        // Assert
        assertTrue(result.isEmpty());
        verify(jpaGestationHealthLogRepository).findByHealthLogId(healthLogId);
    }

    @Test
    void save_shouldSaveAndReturnGestationHealthLog() {
        // Arrange
        GestationHealthLog gestationHealthLog = new GestationHealthLog(
                1L,
                2L,
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(38.5),
                "Normal",
                "Test notes",
                "Normal",
                true
        );
        
        GestationHealthLogEntity entity = GestationHealthLogEntityMapper.toEntity(gestationHealthLog);
        when(jpaGestationHealthLogRepository.save(any(GestationHealthLogEntity.class))).thenReturn(entity);

        // Act
        GestationHealthLog result = gestationHealthLogPersistenceAdapter.save(gestationHealthLog);

        // Assert
        assertEquals(gestationHealthLog.getGestationId(), result.getGestationId());
        assertEquals(gestationHealthLog.getHealthLogId(), result.getHealthLogId());
        assertEquals(gestationHealthLog.getWeight(), result.getWeight());
        assertEquals(gestationHealthLog.getTemperature(), result.getTemperature());
        assertEquals(gestationHealthLog.getBehavior(), result.getBehavior());
        assertEquals(gestationHealthLog.getNotes(), result.getNotes());
        assertEquals(gestationHealthLog.getMammaryObservations(), result.getMammaryObservations());
        assertEquals(gestationHealthLog.getKittenMovement(), result.getKittenMovement());
        
        verify(jpaGestationHealthLogRepository).save(any(GestationHealthLogEntity.class));
    }

    @Test
    void deleteById_shouldCallDeleteOnRepository() {
        // Arrange
        Long id = 1L;

        // Act
        gestationHealthLogPersistenceAdapter.deleteById(id);

        // Assert
        verify(jpaGestationHealthLogRepository).deleteById(id);
    }
}