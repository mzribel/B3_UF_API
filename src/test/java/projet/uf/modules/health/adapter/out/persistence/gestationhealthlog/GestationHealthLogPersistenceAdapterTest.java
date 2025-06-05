package projet.uf.modules.health.adapter.out.persistence.gestationhealthlog;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projet.uf.modules.health.adapter.out.persistence.gestationhealthlog.GestationHealthLogEntity;
import projet.uf.modules.health.adapter.out.persistence.gestationhealthlog.GestationHealthLogPersistenceAdapter;
import projet.uf.modules.health.adapter.out.persistence.gestationhealthlog.JpaGestationHealthLogRepository;
import projet.uf.modules.health.domain.model.GestationHealthLog;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GestationHealthLogPersistenceAdapterTest {

    @Mock
    private JpaGestationHealthLogRepository jpaGestationHealthLogRepository;

    @InjectMocks
    private GestationHealthLogPersistenceAdapter gestationHealthLogPersistenceAdapter;

    @Test
    void getById_shouldReturnGestationHealthLog_whenExists() {
        Long id = 1L;
        GestationHealthLogEntity entity = new GestationHealthLogEntity(
                id, 10L, 20L, "Normal", true
        );

        when(jpaGestationHealthLogRepository.findById(id)).thenReturn(Optional.of(entity));

        Optional<GestationHealthLog> result = gestationHealthLogPersistenceAdapter.getById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(jpaGestationHealthLogRepository).findById(id);
    }

    @Test
    void getById_shouldReturnEmpty_whenNotExists() {
        Long id = 1L;
        when(jpaGestationHealthLogRepository.findById(id)).thenReturn(Optional.empty());

        Optional<GestationHealthLog> result = gestationHealthLogPersistenceAdapter.getById(id);

        assertTrue(result.isEmpty());
        verify(jpaGestationHealthLogRepository).findById(id);
    }

    @Test
    void getByGestationId_shouldReturnGestationHealthLogs() {
        Long gestationId = 10L;
        GestationHealthLogEntity e1 = new GestationHealthLogEntity(1L, gestationId, 101L,
                "OK", true);
        GestationHealthLogEntity e2 = new GestationHealthLogEntity(2L, gestationId, 102L,
                "OK", true);

        when(jpaGestationHealthLogRepository.findByGestationId(gestationId)).thenReturn(List.of(e1, e2));

        List<GestationHealthLog> result = gestationHealthLogPersistenceAdapter.getByGestationId(gestationId);

        assertEquals(2, result.size());
        assertEquals(gestationId, result.getFirst().getGestationId());
        verify(jpaGestationHealthLogRepository).findByGestationId(gestationId);
    }

    @Test
    void getByHealthLogId_shouldReturnGestationHealthLog_whenExists() {
        Long healthLogId = 20L;
        GestationHealthLogEntity entity = new GestationHealthLogEntity(
                1L, 10L, healthLogId, "Ok", false
        );

        when(jpaGestationHealthLogRepository.findByHealthLogId(healthLogId)).thenReturn(Optional.of(entity));

        Optional<GestationHealthLog> result = gestationHealthLogPersistenceAdapter.getByHealthLogId(healthLogId);

        assertTrue(result.isPresent());
        assertEquals(healthLogId, result.get().getHealthLogId());
        verify(jpaGestationHealthLogRepository).findByHealthLogId(healthLogId);
    }

    @Test
    void getByHealthLogId_shouldReturnEmpty_whenNotExists() {
        Long healthLogId = 99L;
        when(jpaGestationHealthLogRepository.findByHealthLogId(healthLogId)).thenReturn(Optional.empty());

        Optional<GestationHealthLog> result = gestationHealthLogPersistenceAdapter.getByHealthLogId(healthLogId);

        assertTrue(result.isEmpty());
        verify(jpaGestationHealthLogRepository).findByHealthLogId(healthLogId);
    }

    @Test
    void save_shouldReturnSavedGestationHealthLog() {
        GestationHealthLog model = new GestationHealthLog(
                10L, 20L,"Normal", true
        );
        GestationHealthLogEntity entity = GestationHealthLogEntity.toEntity(model);
        when(jpaGestationHealthLogRepository.save(any())).thenReturn(entity);

        GestationHealthLog result = gestationHealthLogPersistenceAdapter.save(model);

        assertEquals(model.getGestationId(), result.getGestationId());
        verify(jpaGestationHealthLogRepository).save(any());
    }

    @Test
    void deleteById_shouldCallRepositoryDelete() {
        Long id = 1L;
        gestationHealthLogPersistenceAdapter.deleteById(id);
        verify(jpaGestationHealthLogRepository).deleteById(id);
    }
}
