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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KittenHealthLogPersistenceAdapterTest {

    @Mock
    private JpaKittenHealthLogRepository jpaKittenHealthLogRepository;

    @InjectMocks
    private KittenHealthLogPersistenceAdapter kittenHealthLogPersistenceAdapter;

    @Test
    void getById_shouldReturnKittenHealthLog_whenExists() {
        Long id = 1L;
        LocalDateTime openEyesDate = LocalDateTime.now();

        KittenHealthLogEntity entity = new KittenHealthLogEntity(id, 1L, openEyesDate, "Kitten notes");
        when(jpaKittenHealthLogRepository.findById(id)).thenReturn(Optional.of(entity));

        Optional<KittenHealthLog> result = kittenHealthLogPersistenceAdapter.getById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        assertEquals(entity.getHealthLogId(), result.get().getHealthLogId());
        assertEquals(entity.getOpenEyesDate(), result.get().getOpenEyesDate());

        verify(jpaKittenHealthLogRepository).findById(id);
    }

    @Test
    void getById_shouldReturnEmpty_whenNotExists() {
        Long id = 99L;
        when(jpaKittenHealthLogRepository.findById(id)).thenReturn(Optional.empty());

        Optional<KittenHealthLog> result = kittenHealthLogPersistenceAdapter.getById(id);

        assertTrue(result.isEmpty());
        verify(jpaKittenHealthLogRepository).findById(id);
    }

    @Test
    void getByHealthLogId_shouldReturnKittenHealthLog_whenExists() {
        Long healthLogId = 10L;
        LocalDateTime openEyesDate = LocalDateTime.now();

        KittenHealthLogEntity entity = new KittenHealthLogEntity(1L, healthLogId, openEyesDate, "Kitten notes");
        when(jpaKittenHealthLogRepository.findByHealthLogId(healthLogId)).thenReturn(Optional.of(entity));

        Optional<KittenHealthLog> result = kittenHealthLogPersistenceAdapter.getByHealthLogId(healthLogId);

        assertTrue(result.isPresent());
        assertEquals(healthLogId, result.get().getHealthLogId());
        assertEquals(entity.getOpenEyesDate(), result.get().getOpenEyesDate());


        verify(jpaKittenHealthLogRepository).findByHealthLogId(healthLogId);
    }

    @Test
    void getByHealthLogId_shouldReturnEmpty_whenNotExists() {
        Long healthLogId = 10L;
        when(jpaKittenHealthLogRepository.findByHealthLogId(healthLogId)).thenReturn(Optional.empty());

        Optional<KittenHealthLog> result = kittenHealthLogPersistenceAdapter.getByHealthLogId(healthLogId);

        assertTrue(result.isEmpty());
        verify(jpaKittenHealthLogRepository).findByHealthLogId(healthLogId);
    }

    @Test
    void save_shouldSaveAndReturnKittenHealthLog() {
        LocalDateTime openEyesDate = LocalDateTime.now();

        KittenHealthLog model = new KittenHealthLog(1L, openEyesDate, "Kitten notes");
        KittenHealthLogEntity entity = KittenHealthLogEntity.toEntity(model);

        when(jpaKittenHealthLogRepository.save(any())).thenReturn(entity);

        KittenHealthLog result = kittenHealthLogPersistenceAdapter.save(model);

        assertEquals(model.getHealthLogId(), result.getHealthLogId());
        assertEquals(model.getOpenEyesDate(), result.getOpenEyesDate());

        verify(jpaKittenHealthLogRepository).save(any());
    }

    @Test
    void deleteById_shouldCallRepository() {
        Long id = 5L;

        kittenHealthLogPersistenceAdapter.deleteById(id);

        verify(jpaKittenHealthLogRepository).deleteById(id);
    }
}
