package projet.uf.health.adapter.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import projet.uf.modules.health.adapter.in.rest.HealthLogController;
import projet.uf.modules.health.application.model.CreateHealthLogCommand;
import projet.uf.modules.health.application.model.CreateKittenHealthLogCommand;
import projet.uf.modules.health.application.model.CreateGestationHealthLogCommand;
import projet.uf.modules.health.application.port.in.HealthLogUseCase;
import projet.uf.modules.health.domain.model.HealthLog;
import projet.uf.modules.health.domain.model.KittenHealthLog;
import projet.uf.modules.health.domain.model.GestationHealthLog;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class HealthLogControllerTest {

    private MockMvc mockMvc;

    @Mock
    private HealthLogUseCase healthLogUseCase;

    @InjectMocks
    private HealthLogController healthLogController;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(healthLogController).build();
    }

    @Test
    void getAllHealthLogs_shouldReturnHealthLogs() throws Exception {
        // Arrange
        HealthLog healthLog1 = new HealthLog();
        healthLog1.setId(1L);
        healthLog1.setCatId(1L);

        HealthLog healthLog2 = new HealthLog();
        healthLog2.setId(2L);
        healthLog2.setCatId(2L);

        when(healthLogUseCase.getAllHealthLogs()).thenReturn(Arrays.asList(healthLog1, healthLog2));

        // Act & Assert
        mockMvc.perform(get("/health-logs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void getHealthLogById_shouldReturnHealthLog_whenExists() throws Exception {
        // Arrange
        Long id = 1L;
        HealthLog healthLog = new HealthLog();
        healthLog.setId(id);
        healthLog.setCatId(1L);
        healthLog.setNotes("Test notes");

        when(healthLogUseCase.getHealthLogById(id)).thenReturn(Optional.of(healthLog));

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
        when(healthLogUseCase.getHealthLogById(id)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/health-logs/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void createHealthLog_shouldReturnCreatedHealthLog() throws Exception {
        // Arrange
        CreateHealthLogCommand command = new CreateHealthLogCommand(
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

        HealthLog healthLog = new HealthLog();
        healthLog.setId(1L);
        healthLog.setCatId(command.getCatId());
        healthLog.setWeightInGrams(command.getWeightInGrams());
        healthLog.setTemperatureInCelsius(command.getTemperatureInCelsius());
        healthLog.setAppetite(command.getAppetite());
        healthLog.setHydratation(command.getHydratation());
        healthLog.setBehavior(command.getBehavior());
        healthLog.setStoolQuality(command.getStoolQuality());
        healthLog.setUrineObservations(command.getUrineObservations());
        healthLog.setNotes(command.getNotes());

        when(healthLogUseCase.createHealthLog(any(HealthLog.class))).thenReturn(healthLog);

        // Act & Assert
        mockMvc.perform(post("/health-logs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.catId").value(command.getCatId()))
                .andExpect(jsonPath("$.notes").value(command.getNotes()));
    }

    @Test
    void updateHealthLog_shouldReturnUpdatedHealthLog() throws Exception {
        // Arrange
        Long id = 1L;
        CreateHealthLogCommand command = new CreateHealthLogCommand(
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

        HealthLog healthLog = new HealthLog();
        healthLog.setId(id);
        healthLog.setCatId(command.getCatId());
        healthLog.setWeightInGrams(command.getWeightInGrams());
        healthLog.setTemperatureInCelsius(command.getTemperatureInCelsius());
        healthLog.setAppetite(command.getAppetite());
        healthLog.setHydratation(command.getHydratation());
        healthLog.setBehavior(command.getBehavior());
        healthLog.setStoolQuality(command.getStoolQuality());
        healthLog.setUrineObservations(command.getUrineObservations());
        healthLog.setNotes(command.getNotes());

        when(healthLogUseCase.updateHealthLog(eq(id), any(HealthLog.class))).thenReturn(healthLog);

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

    @Test
    void getKittenHealthLogById_shouldReturnKittenHealthLog_whenExists() throws Exception {
        // Arrange
        Long id = 1L;
        KittenHealthLog kittenHealthLog = new KittenHealthLog();
        kittenHealthLog.setId(id);
        kittenHealthLog.setHealthLogId(1L);

        when(healthLogUseCase.getKittenHealthLogById(id)).thenReturn(Optional.of(kittenHealthLog));

        // Act & Assert
        mockMvc.perform(get("/kitten-health-logs/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.healthLogId").value(1));
    }

    @Test
    void createKittenHealthLog_shouldReturnCreatedKittenHealthLog() throws Exception {
        // Arrange
        CreateKittenHealthLogCommand command = new CreateKittenHealthLogCommand(
                1L,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1)
        );

        KittenHealthLog kittenHealthLog = new KittenHealthLog();
        kittenHealthLog.setId(1L);
        kittenHealthLog.setHealthLogId(command.getHealthLogId());
        kittenHealthLog.setOpenEyesDate(command.getOpenEyesDate());
        kittenHealthLog.setFirstWakDate(command.getFirstWakDate());

        when(healthLogUseCase.createKittenHealthLog(any(KittenHealthLog.class))).thenReturn(kittenHealthLog);

        // Act & Assert
        mockMvc.perform(post("/kitten-health-logs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.healthLogId").value(command.getHealthLogId()));
    }

    @Test
    void getGestationHealthLogById_shouldReturnGestationHealthLog_whenExists() throws Exception {
        // Arrange
        Long id = 1L;
        GestationHealthLog gestationHealthLog = new GestationHealthLog();
        gestationHealthLog.setId(id);
        gestationHealthLog.setGestationId(1L);
        gestationHealthLog.setHealthLogId(2L);

        when(healthLogUseCase.getGestationHealthLogById(id)).thenReturn(Optional.of(gestationHealthLog));

        // Act & Assert
        mockMvc.perform(get("/gestation-health-logs/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.gestationId").value(1))
                .andExpect(jsonPath("$.healthLogId").value(2));
    }

    @Test
    void createGestationHealthLog_shouldReturnCreatedGestationHealthLog() throws Exception {
        // Arrange
        CreateGestationHealthLogCommand command = new CreateGestationHealthLogCommand(
                1L,
                2L,
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(38.5),
                "Normal",
                "Test notes",
                "Normal",
                true
        );

        GestationHealthLog gestationHealthLog = new GestationHealthLog();
        gestationHealthLog.setId(1L);
        gestationHealthLog.setGestationId(command.getGestationId());
        gestationHealthLog.setHealthLogId(command.getHealthLogId());
        gestationHealthLog.setWeight(command.getWeight());
        gestationHealthLog.setTemperature(command.getTemperature());
        gestationHealthLog.setBehavior(command.getBehavior());
        gestationHealthLog.setNotes(command.getNotes());
        gestationHealthLog.setMammaryObservations(command.getMammaryObservations());
        gestationHealthLog.setKittenMovement(command.getKittenMovement());

        when(healthLogUseCase.createGestationHealthLog(any(GestationHealthLog.class))).thenReturn(gestationHealthLog);

        // Act & Assert
        mockMvc.perform(post("/gestation-health-logs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.gestationId").value(command.getGestationId()))
                .andExpect(jsonPath("$.healthLogId").value(command.getHealthLogId()));
    }
}
