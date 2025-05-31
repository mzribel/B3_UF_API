package projet.uf.health.adapter.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import projet.uf.exceptions.ApiException;
import projet.uf.exceptions.ExceptionControllerAdvice;
import projet.uf.modules.auth.adapters.in.rest.security.CurrentUserProvider;
import projet.uf.modules.auth.application.model.CurrentUser;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.health.adapter.in.rest.HealthLogController;
import projet.uf.modules.health.application.command.CreateHealthLogCommand;
import projet.uf.modules.health.application.command.CreateKittenHealthLogCommand;
import projet.uf.modules.health.application.command.CreateGestationHealthLogCommand;
import projet.uf.modules.health.application.port.in.HealthLogUseCase;
import projet.uf.modules.health.domain.model.HealthLog;
import projet.uf.modules.health.domain.model.KittenHealthLog;
import projet.uf.modules.health.domain.model.GestationHealthLog;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class HealthLogControllerTest {

    private MockMvc mockMvc;

    @Mock
    private HealthLogUseCase healthLogUseCase;

    @Mock
    private CurrentUserProvider currentUserProvider;

    @InjectMocks
    private HealthLogController healthLogController;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(healthLogController)
                .setControllerAdvice(new ExceptionControllerAdvice())
                .build();

        CurrentUser mockUserDetails = mock(CurrentUser.class);
        when(currentUserProvider.getCurrentUser()).thenReturn(mockUserDetails);
    }

    @Test
    void getAllHealthLogs_shouldReturnHealthLogs() throws Exception {
        // Arrange
        HealthLog healthLog1 = HealthLog.builder()
                .id(1L)
                .catId(101L)
                .weightInGrams(BigDecimal.valueOf(3000))
                .temperatureInCelsius(BigDecimal.valueOf(38.5))
                .date(LocalDateTime.of(2024, 1, 1, 12, 0))
                .build();

        HealthLog healthLog2 = HealthLog.builder()
                .id(2L)
                .catId(102L)
                .weightInGrams(BigDecimal.valueOf(3200))
                .temperatureInCelsius(BigDecimal.valueOf(38.2))
                .date(LocalDateTime.of(2024, 1, 1, 12, 0))
                .build();

        when(healthLogUseCase.getAllHealthLogs(any(OperatorUser.class)))
                .thenReturn(List.of(healthLog1, healthLog2));

        // Act & Assert
        mockMvc.perform(get("/health-logs")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].catId").value(101))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].catId").value(102));
    }

    @Test
    void getHealthLogById_shouldReturnHealthLog_whenExists() throws Exception {
        // Arrange
        Long id = 1L;
        HealthLog healthLog = new HealthLog();
        healthLog.setId(id);
        healthLog.setCatId(1L);
        healthLog.setNotes("Test notes");

        when(healthLogUseCase.getHealthLogById(eq(id), any(OperatorUser.class))).thenReturn(healthLog);

        // Act & Assert
        mockMvc.perform(get("/health-logs/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.notes").value("Test notes"));
    }

    @Test
    void getHealthLogById_shouldReturn404_whenNotExists() throws Exception {
        // Arrange
        Long id = 1L;
        when(healthLogUseCase.getHealthLogById(eq(id), any(OperatorUser.class)))
                .thenThrow(new ApiException("Entrée de santé introudddvable", HttpStatus.NOT_FOUND));

        // Act & Assert
        mockMvc.perform(get("/health-logs/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void createHealthLog_shouldReturnCreatedHealthLog() throws Exception {
        // Arrange
        Long catId = 1L;
        CreateHealthLogCommand command = new CreateHealthLogCommand(
            BigDecimal.valueOf(1000),
            BigDecimal.valueOf(38.5),
            "Good",
            "Normal",
            "Active",
            "Normal",
            "Normal",
            "Test notes",
            LocalDateTime.of(2024, 1, 1, 12, 0)
        );

        HealthLog healthLog = command.toHealthLog(catId);
        healthLog.setId(1L);

        when(healthLogUseCase.createHealthLog(eq(catId), any(CreateHealthLogCommand.class), any(OperatorUser.class)))
                .thenReturn(healthLog);

        // Act & Assert
        mockMvc.perform(post("/cats/{catId}/health-logs", catId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated());
    }


    @Test
    void updateHealthLog_shouldReturnUpdatedHealthLog() throws Exception {
        // Arrange
        Long id = 1L;
        CreateHealthLogCommand command = new CreateHealthLogCommand(
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(38.5),
                "Good",
                "Normal",
                "Active",
                "Normal",
                "Normal",
                "Updated notes",
                LocalDateTime.of(2024, 1, 1, 12, 0)
        );

        HealthLog healthLog = command.toHealthLog(1L);
        healthLog.setId(id);

        when(healthLogUseCase.updateHealthLog(eq(id), any(CreateHealthLogCommand.class), any(OperatorUser.class)))
                .thenReturn(healthLog);

        // Act & Assert
        mockMvc.perform(put("/health-logs/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.notes").value("Updated notes"));
    }

    @Test
    void deleteHealthLog_shouldReturn204() throws Exception {
        // Arrange
        Long id = 1L;

        // Act & Assert
        mockMvc.perform(delete("/health-logs/{id}", id))
                .andExpect(status().isNoContent());
    }

//    @Test
//    void getKittenHealthLogById_shouldReturnKittenHealthLog_whenExists() throws Exception {
//        // Arrange
//        Long id = 1L;
//        KittenHealthLog kittenHealthLog = new KittenHealthLog();
//        kittenHealthLog.setId(id);
//        kittenHealthLog.setHealthLogId(1L);
//
//        when(healthLogUseCase.getHealthLogById(id)).thenReturn(Optional.of(kittenHealthLog));
//
//        // Act & Assert
//        mockMvc.perform(get("/kitten-health-logs/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(id))
//                .andExpect(jsonPath("$.healthLogId").value(1));
//    }

    @Test
    void createKittenHealthLog_shouldReturnCreatedKittenHealthLog() throws Exception {
        // Arrange
        Long healthLogId = 42L;
        CreateKittenHealthLogCommand command = new CreateKittenHealthLogCommand(
                LocalDateTime.of(2024, 1, 1, 12, 0),
                "Kitten notes"
        );

        KittenHealthLog expected = new KittenHealthLog();
        expected.setId(1L);
        expected.setHealthLogId(healthLogId);
        expected.setOpenEyesDate(command.openEyesDate());

        when(healthLogUseCase.createKittenHealthLog(eq(healthLogId), any(CreateKittenHealthLogCommand.class), any(OperatorUser.class)))
                .thenReturn(expected);

        // Act & Assert
        mockMvc.perform(post("/health-logs/{healthLogId}/kitten", healthLogId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.healthLogId").value(healthLogId));
    }

//    @Test
//    void getGestationHealthLogById_shouldReturnGestationHealthLog_whenExists() throws Exception {
//        // Arrange
//        Long id = 1L;
//        GestationHealthLog gestationHealthLog = new GestationHealthLog();
//        gestationHealthLog.setId(id);
//        gestationHealthLog.setGestationId(1L);
//        gestationHealthLog.setHealthLogId(2L);
//
//        when(healthLogUseCase.getGestationHealthLogById(id)).thenReturn(Optional.of(gestationHealthLog));
//
//        // Act & Assert
//        mockMvc.perform(get("/gestation-health-logs/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(id))
//                .andExpect(jsonPath("$.gestationId").value(1))
//                .andExpect(jsonPath("$.healthLogId").value(2));
//    }

    @Test
    void createGestationHealthLog_shouldReturnCreatedGestationHealthLog() throws Exception {
        Long healthLogId = 42L;
        Long gestationId = 7L;

        // Arrange
        CreateGestationHealthLogCommand command = new CreateGestationHealthLogCommand(
                "Normal",
                true
        );

        GestationHealthLog expected = command.toModel(healthLogId);
        expected.setId(1L);
        expected.setGestationId(gestationId);

        when(healthLogUseCase.createGestationHealthLog(eq(healthLogId), any(CreateGestationHealthLogCommand.class), any(OperatorUser.class)))
                .thenReturn(expected);

        // Act & Assert
        mockMvc.perform(post("/health-logs/{healthLogId}/gestation", healthLogId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.gestationId").value(gestationId))
                .andExpect(jsonPath("$.healthLogId").value(healthLogId));
    }
}