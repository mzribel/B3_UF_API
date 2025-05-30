package projet.uf.health.application.model;

import org.junit.jupiter.api.Test;
import projet.uf.modules.health.application.model.CreateGestationHealthLogCommand;
import projet.uf.modules.health.application.model.CreateHealthLogCommand;
import projet.uf.modules.health.application.model.CreateKittenHealthLogCommand;
import projet.uf.modules.health.application.model.HealthLogCommandMapper;
import projet.uf.modules.health.domain.model.GestationHealthLog;
import projet.uf.modules.health.domain.model.HealthLog;
import projet.uf.modules.health.domain.model.KittenHealthLog;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class HealthLogCommandMapperTest {

    @Test
    void fromCreateHealthLogCommand_shouldMapAllFields() {
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

        // Act
        HealthLog result = HealthLogCommandMapper.fromCreateHealthLogCommand(command);

        // Assert
        assertEquals(command.getCatId(), result.getCatId());
        assertEquals(command.getWeightInGrams(), result.getWeightInGrams());
        assertEquals(command.getTemperatureInCelsius(), result.getTemperatureInCelsius());
        assertEquals(command.getAppetite(), result.getAppetite());
        assertEquals(command.getHydratation(), result.getHydratation());
        assertEquals(command.getBehavior(), result.getBehavior());
        assertEquals(command.getStoolQuality(), result.getStoolQuality());
        assertEquals(command.getUrineObservations(), result.getUrineObservations());
        assertEquals(command.getNotes(), result.getNotes());
        assertNull(result.getId()); // ID should be null as it's not set by the mapper
    }

    @Test
    void fromCreateKittenHealthLogCommand_shouldMapAllFields() {
        // Arrange
        LocalDateTime openEyesDate = LocalDateTime.now();
        LocalDateTime firstWakDate = LocalDateTime.now().plusDays(1);
        
        CreateKittenHealthLogCommand command = new CreateKittenHealthLogCommand(
                1L,
                openEyesDate,
                firstWakDate
        );

        // Act
        KittenHealthLog result = HealthLogCommandMapper.fromCreateKittenHealthLogCommand(command);

        // Assert
        assertEquals(command.getHealthLogId(), result.getHealthLogId());
        assertEquals(command.getOpenEyesDate(), result.getOpenEyesDate());
        assertEquals(command.getFirstWakDate(), result.getFirstWakDate());
        assertNull(result.getId()); // ID should be null as it's not set by the mapper
    }

    @Test
    void fromCreateGestationHealthLogCommand_shouldMapAllFields() {
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

        // Act
        GestationHealthLog result = HealthLogCommandMapper.fromCreateGestationHealthLogCommand(command);

        // Assert
        assertEquals(command.getGestationId(), result.getGestationId());
        assertEquals(command.getHealthLogId(), result.getHealthLogId());
        assertEquals(command.getWeight(), result.getWeight());
        assertEquals(command.getTemperature(), result.getTemperature());
        assertEquals(command.getBehavior(), result.getBehavior());
        assertEquals(command.getNotes(), result.getNotes());
        assertEquals(command.getMammaryObservations(), result.getMammaryObservations());
        assertEquals(command.getKittenMovement(), result.getKittenMovement());
        assertNull(result.getId()); // ID should be null as it's not set by the mapper
    }
}