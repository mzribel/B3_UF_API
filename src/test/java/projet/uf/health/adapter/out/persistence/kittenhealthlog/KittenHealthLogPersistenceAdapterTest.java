package projet.uf.health.adapter.out.persistence.kittenhealthlog;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projet.uf.modules.health.adapter.out.persistence.kittenhealthlog.KittenHealthLogEntity;
import projet.uf.modules.health.adapter.out.persistence.kittenhealthlog.KittenHealthLogPersistenceAdapter;
import projet.uf.modules.health.adapter.out.persistence.kittenhealthlog.JpaKittenHealthLogRepository;
import projet.uf.modules.health.domain.model.KittenHealthLog;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KittenHealthLogPersistenceAdapterTest {
//
//    @Mock
//    private JpaKittenHealthLogRepository jpaKittenHealthLogRepository;
//
//    @InjectMocks
//    private KittenHealthLogPersistenceAdapter kittenHealthLogPersistenceAdapter;
//
//    @Test
//    void getById_shouldReturnKittenHealthLog_whenExists() {
//        // Arrange
//        Long id = 1L;
//        LocalDateTime openEyesDate = LocalDateTime.now();
//        LocalDateTime firstWakDate = LocalDateTime.now().plusDays(1);
//
//        KittenHealthLogEntity entity = new KittenHealthLogEntity(
//                id,
//                1L,
//                openEyesDate,
//                firstWakDate
//        );
//
//        when(jpaKittenHealthLogRepository.findById(id)).thenReturn(Optional.of(entity));
//
//        // Act
//        Optional<KittenHealthLog> result = kittenHealthLogPersistenceAdapter.getById(id);
//
//        // Assert
//        assertTrue(result.isPresent());
//        assertEquals(id, result.get().getId());
//        assertEquals(entity.getHealthLogId(), result.get().getHealthLogId());
//        assertEquals(entity.getOpenEyesDate(), result.get().getOpenEyesDate());
//        assertEquals(entity.getFirstWakDate(), result.get().getFirstWakDate());
//
//        verify(jpaKittenHealthLogRepository).findById(id);
//    }
//
//    @Test
//    void getById_shouldReturnEmpty_whenNotExists() {
//        // Arrange
//        Long id = 1L;
//        when(jpaKittenHealthLogRepository.findById(id)).thenReturn(Optional.empty());
//
//        // Act
//        Optional<KittenHealthLog> result = kittenHealthLogPersistenceAdapter.getById(id);
//
//        // Assert
//        assertTrue(result.isEmpty());
//        verify(jpaKittenHealthLogRepository).findById(id);
//    }
//
//    @Test
//    void getByHealthLogId_shouldReturnKittenHealthLog_whenExists() {
//        // Arrange
//        Long healthLogId = 1L;
//        LocalDateTime openEyesDate = LocalDateTime.now();
//        LocalDateTime firstWakDate = LocalDateTime.now().plusDays(1);
//
//        KittenHealthLogEntity entity = new KittenHealthLogEntity(
//                1L,
//                healthLogId,
//                openEyesDate,
//                firstWakDate
//        );
//
//        when(jpaKittenHealthLogRepository.findByHealthLogId(healthLogId)).thenReturn(Optional.of(entity));
//
//        // Act
//        Optional<KittenHealthLog> result = kittenHealthLogPersistenceAdapter.getByHealthLogId(healthLogId);
//
//        // Assert
//        assertTrue(result.isPresent());
//        assertEquals(healthLogId, result.get().getHealthLogId());
//        assertEquals(entity.getOpenEyesDate(), result.get().getOpenEyesDate());
//        assertEquals(entity.getFirstWakDate(), result.get().getFirstWakDate());
//
//        verify(jpaKittenHealthLogRepository).findByHealthLogId(healthLogId);
//    }
//
//    @Test
//    void getByHealthLogId_shouldReturnEmpty_whenNotExists() {
//        // Arrange
//        Long healthLogId = 1L;
//        when(jpaKittenHealthLogRepository.findByHealthLogId(healthLogId)).thenReturn(Optional.empty());
//
//        // Act
//        Optional<KittenHealthLog> result = kittenHealthLogPersistenceAdapter.getByHealthLogId(healthLogId);
//
//        // Assert
//        assertTrue(result.isEmpty());
//        verify(jpaKittenHealthLogRepository).findByHealthLogId(healthLogId);
//    }
//
//    @Test
//    void save_shouldSaveAndReturnKittenHealthLog() {
//        // Arrange
//        LocalDateTime openEyesDate = LocalDateTime.now();
//        LocalDateTime firstWakDate = LocalDateTime.now().plusDays(1);
//
//        KittenHealthLog kittenHealthLog = new KittenHealthLog(
//                1L,
//                openEyesDate,
//                firstWakDate
//        );
//
//        KittenHealthLogEntity entity = KittenHealthLogEntityMapper.toEntity(kittenHealthLog);
//        when(jpaKittenHealthLogRepository.save(any(KittenHealthLogEntity.class))).thenReturn(entity);
//
//        // Act
//        KittenHealthLog result = kittenHealthLogPersistenceAdapter.save(kittenHealthLog);
//
//        // Assert
//        assertEquals(kittenHealthLog.getHealthLogId(), result.getHealthLogId());
//        assertEquals(kittenHealthLog.getOpenEyesDate(), result.getOpenEyesDate());
//        assertEquals(kittenHealthLog.getFirstWakDate(), result.getFirstWakDate());
//
//        verify(jpaKittenHealthLogRepository).save(any(KittenHealthLogEntity.class));
//    }
//
//    @Test
//    void deleteById_shouldCallDeleteOnRepository() {
//        // Arrange
//        Long id = 1L;
//
//        // Act
//        kittenHealthLogPersistenceAdapter.deleteById(id);
//
//        // Assert
//        verify(jpaKittenHealthLogRepository).deleteById(id);
//    }
}