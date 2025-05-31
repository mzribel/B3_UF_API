package projet.uf.health.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.cat.application.ports.in.CatAuthorizationUseCase;
import projet.uf.modules.health.application.command.CreateHealthLogCommand;
import projet.uf.modules.health.application.command.CreateKittenHealthLogCommand;
import projet.uf.modules.health.application.HealthLogService;
import projet.uf.modules.health.application.port.in.HealthLogAuthorizationUseCase;
import projet.uf.modules.health.application.port.out.HealthLogPersistencePort;
import projet.uf.modules.health.application.port.out.KittenHealthLogPersistencePort;
import projet.uf.modules.health.domain.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HealthLogServiceTest {

    @Mock private HealthLogPersistencePort healthLogPersistencePort;
    @Mock private KittenHealthLogPersistencePort kittenHealthLogPersistencePort;
    @Mock private CatAuthorizationUseCase catAccessUseCase;
    @Mock private HealthLogAuthorizationUseCase healthLogAccessUseCase;

    @InjectMocks private HealthLogService healthLogService;

    private OperatorUser operator;

    @BeforeEach
    void setup() {
        operator = new OperatorUser(1L, false);
    }

    @Test
    void createHealthLog_shouldSaveLog() {
        Long catId = 10L;
        CreateHealthLogCommand command = new CreateHealthLogCommand(
                BigDecimal.valueOf(2000), BigDecimal.valueOf(38.3), "Good", "Hydrated", "Calm", "Solid", "Normal", "RAS", LocalDateTime.now()
        );

        HealthLog saved = HealthLog.builder().id(1L).catId(catId).build();

        when(healthLogPersistencePort.save(any())).thenReturn(saved);

        HealthLog result = healthLogService.createHealthLog(catId, command, operator);

        assertEquals(1L, result.getId());
        verify(catAccessUseCase).getCatOrThrow(catId, operator);
        verify(healthLogPersistencePort).save(any(HealthLog.class));
    }

    @Test
    void createKittenHealthLog_shouldSaveLog() {
        Long healthLogId = 1L;
        Long catId = 42L;
        HealthLog healthLog = HealthLog.builder().id(healthLogId).catId(catId).build();

        CreateKittenHealthLogCommand command = new CreateKittenHealthLogCommand(
                LocalDateTime.now(), "Kitten notes"
        );

        // 👇 mock utilisé réellement
        when(healthLogAccessUseCase.getHealthLogOrThrow(healthLogId, operator)).thenReturn(healthLog);
        when(kittenHealthLogPersistencePort.save(any())).thenAnswer(i -> i.getArgument(0));

        KittenHealthLog result = healthLogService.createKittenHealthLog(healthLogId, command, operator);

        assertEquals(healthLogId, result.getHealthLogId());
        verify(kittenHealthLogPersistencePort).save(any());
    }

    @Test
    void getAllHealthLogs_shouldThrowIfNotAdmin() {
        OperatorUser nonAdmin = new OperatorUser(2L, false);
        ApiException exception = assertThrows(ApiException.class, () -> healthLogService.getAllHealthLogs(nonAdmin));

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
    }

    @Test
    void getHealthLogById_shouldReturnLog_whenAccessGranted() {
        Long id = 5L;
        HealthLog log = HealthLog.builder().id(id).catId(999L).build();

        when(healthLogAccessUseCase.getHealthLogOrThrow(id, operator)).thenReturn(log);

        HealthLog result = healthLogService.getHealthLogById(id, operator);

        assertEquals(id, result.getId());
    }

    @Test
    void getHealthLogsByCatId_shouldReturnLogs_ifAccessOk() {
        Long catId = 10L;
        List<HealthLog> logs = List.of(
                HealthLog.builder().id(1L).catId(catId).build(),
                HealthLog.builder().id(2L).catId(catId).build()
        );

        when(catAccessUseCase.hasUserAccessToCat(catId, operator)).thenReturn(true);
        when(healthLogPersistencePort.getByCatId(catId)).thenReturn(logs);

        List<HealthLog> result = healthLogService.getHealthLogsByCatId(catId, operator);
        assertEquals(2, result.size());
        verify(healthLogPersistencePort).getByCatId(catId);
    }

    @Test
    void updateHealthLog_shouldUpdateLog() {
        Long id = 4L;
        Long catId = 100L;

        // Entité existante simulée
        HealthLog existing = HealthLog.builder().id(id).catId(catId).build();

        // Commande mise à jour
        CreateHealthLogCommand command = new CreateHealthLogCommand(
                BigDecimal.valueOf(1900),
                BigDecimal.valueOf(38.1),
                "Normal", "Hydrated", "Active", "Normal", "Normal",
                "Updated", LocalDateTime.now()
        );

        // Mocks nécessaires
        when(healthLogAccessUseCase.getHealthLogOrThrow(id, operator)).thenReturn(existing);
        when(healthLogPersistencePort.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Appel de la méthode testée
        HealthLog result = healthLogService.updateHealthLog(id, command, operator);

        // Vérifications
        assertEquals(id, result.getId());
        assertEquals(catId, result.getCatId());
        verify(healthLogPersistencePort).save(any());
    }

    @Test
    void deleteHealthLogById_shouldDelete_whenAccessOk() {
        Long id = 10L;
        Long catId = 50L;

        HealthLog log = HealthLog.builder().id(id).catId(catId).build();

        when(healthLogPersistencePort.getById(id)).thenReturn(Optional.of(log));
        when(healthLogAccessUseCase.hasUserAccessToHealthLog(id, operator)).thenReturn(true);

        healthLogService.deleteHealthLogById(id, operator);
        verify(healthLogPersistencePort).deleteById(id);
    }
}
