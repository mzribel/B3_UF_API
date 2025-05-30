package projet.uf.health.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projet.uf.modules.health.application.port.HealthLogService;
import projet.uf.modules.health.application.port.out.HealthLogPersistencePort;
import projet.uf.modules.health.application.port.out.KittenHealthLogPersistencePort;
import projet.uf.modules.health.application.port.out.GestationHealthLogPersistencePort;
import projet.uf.modules.health.domain.model.HealthLog;
import projet.uf.modules.health.domain.model.KittenHealthLog;
import projet.uf.modules.health.domain.model.GestationHealthLog;

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
public class HealthLogServiceTest {

    @Mock
    private HealthLogPersistencePort healthLogPersistencePort;

    @Mock
    private KittenHealthLogPersistencePort kittenHealthLogPersistencePort;

    @Mock
    private GestationHealthLogPersistencePort gestationHealthLogPersistencePort;

    @InjectMocks
    private HealthLogService healthLogService;

    @Test
    void createHealthLog_shouldSaveAndReturnHealthLog() {
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
        
        when(healthLogPersistencePort.save(any(HealthLog.class))).thenReturn(healthLog);

        // Act
        HealthLog result = healthLogService.createHealthLog(healthLog);

        // Assert
        assertEquals(healthLog.getCatId(), result.getCatId());
        assertEquals(healthLog.getWeightInGrams(), result.getWeightInGrams());
        assertEquals(healthLog.getTemperatureInCelsius(), result.getTemperatureInCelsius());
        
        verify(healthLogPersistencePort).save(healthLog);
    }

    @Test
    void getHealthLogById_shouldReturnHealthLog_whenExists() {
        // Arrange
        Long id = 1L;
        HealthLog healthLog = new HealthLog();
        healthLog.setId(id);
        
        when(healthLogPersistencePort.getById(id)).thenReturn(Optional.of(healthLog));

        // Act
        Optional<HealthLog> result = healthLogService.getHealthLogById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        
        verify(healthLogPersistencePort).getById(id);
    }

    @Test
    void getAllHealthLogs_shouldReturnAllHealthLogs() {
        // Arrange
        HealthLog healthLog1 = new HealthLog();
        healthLog1.setId(1L);
        HealthLog healthLog2 = new HealthLog();
        healthLog2.setId(2L);
        List<HealthLog> healthLogs = Arrays.asList(healthLog1, healthLog2);
        
        when(healthLogPersistencePort.getAll()).thenReturn(healthLogs);

        // Act
        List<HealthLog> result = healthLogService.getAllHealthLogs();

        // Assert
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        
        verify(healthLogPersistencePort).getAll();
    }

    @Test
    void getHealthLogsByCatId_shouldReturnHealthLogsForCat() {
        // Arrange
        Long catId = 1L;
        HealthLog healthLog1 = new HealthLog();
        healthLog1.setId(1L);
        healthLog1.setCatId(catId);
        HealthLog healthLog2 = new HealthLog();
        healthLog2.setId(2L);
        healthLog2.setCatId(catId);
        List<HealthLog> healthLogs = Arrays.asList(healthLog1, healthLog2);
        
        when(healthLogPersistencePort.getByCatId(catId)).thenReturn(healthLogs);

        // Act
        List<HealthLog> result = healthLogService.getHealthLogsByCatId(catId);

        // Assert
        assertEquals(2, result.size());
        assertEquals(catId, result.get(0).getCatId());
        assertEquals(catId, result.get(1).getCatId());
        
        verify(healthLogPersistencePort).getByCatId(catId);
    }

    @Test
    void updateHealthLog_shouldUpdateAndReturnHealthLog() {
        // Arrange
        Long id = 1L;
        HealthLog healthLog = new HealthLog(
                1L, 
                BigDecimal.valueOf(1000), 
                BigDecimal.valueOf(38.5), 
                "Good", 
                "Normal", 
                "Active", 
                "Normal", 
                "Normal", 
                "Updated notes"
        );
        
        when(healthLogPersistencePort.save(any(HealthLog.class))).thenReturn(healthLog);

        // Act
        HealthLog result = healthLogService.updateHealthLog(id, healthLog);

        // Assert
        assertEquals(id, result.getId());
        
        ArgumentCaptor<HealthLog> captor = ArgumentCaptor.forClass(HealthLog.class);
        verify(healthLogPersistencePort).save(captor.capture());
        assertEquals(id, captor.getValue().getId());
    }

    @Test
    void deleteHealthLogById_shouldCallDeleteOnPersistencePort() {
        // Arrange
        Long id = 1L;

        // Act
        healthLogService.deleteHealthLogById(id);

        // Assert
        verify(healthLogPersistencePort).deleteById(id);
    }

    @Test
    void createKittenHealthLog_shouldSaveAndReturnKittenHealthLog() {
        // Arrange
        KittenHealthLog kittenHealthLog = new KittenHealthLog(
                1L,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1)
        );
        
        when(kittenHealthLogPersistencePort.save(any(KittenHealthLog.class))).thenReturn(kittenHealthLog);

        // Act
        KittenHealthLog result = healthLogService.createKittenHealthLog(kittenHealthLog);

        // Assert
        assertEquals(kittenHealthLog.getHealthLogId(), result.getHealthLogId());
        
        verify(kittenHealthLogPersistencePort).save(kittenHealthLog);
    }

    @Test
    void getKittenHealthLogById_shouldReturnKittenHealthLog_whenExists() {
        // Arrange
        Long id = 1L;
        KittenHealthLog kittenHealthLog = new KittenHealthLog();
        kittenHealthLog.setId(id);
        
        when(kittenHealthLogPersistencePort.getById(id)).thenReturn(Optional.of(kittenHealthLog));

        // Act
        Optional<KittenHealthLog> result = healthLogService.getKittenHealthLogById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        
        verify(kittenHealthLogPersistencePort).getById(id);
    }

    @Test
    void createGestationHealthLog_shouldSaveAndReturnGestationHealthLog() {
        // Arrange
        GestationHealthLog gestationHealthLog = new GestationHealthLog(
                1L,
                1L,
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(38.5),
                "Normal",
                "Test notes",
                "Normal",
                true
        );
        
        when(gestationHealthLogPersistencePort.save(any(GestationHealthLog.class))).thenReturn(gestationHealthLog);

        // Act
        GestationHealthLog result = healthLogService.createGestationHealthLog(gestationHealthLog);

        // Assert
        assertEquals(gestationHealthLog.getGestationId(), result.getGestationId());
        assertEquals(gestationHealthLog.getHealthLogId(), result.getHealthLogId());
        
        verify(gestationHealthLogPersistencePort).save(gestationHealthLog);
    }

    @Test
    void getGestationHealthLogById_shouldReturnGestationHealthLog_whenExists() {
        // Arrange
        Long id = 1L;
        GestationHealthLog gestationHealthLog = new GestationHealthLog();
        gestationHealthLog.setId(id);
        
        when(gestationHealthLogPersistencePort.getById(id)).thenReturn(Optional.of(gestationHealthLog));

        // Act
        Optional<GestationHealthLog> result = healthLogService.getGestationHealthLogById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        
        verify(gestationHealthLogPersistencePort).getById(id);
    }
}