package projet.uf.modules.breeder.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.BreederService;
import projet.uf.modules.breeder.application.command.CreateContactBreederCommand;
import projet.uf.modules.breeder.application.command.UpdateCatteryBreederCommand;
import projet.uf.modules.breeder.application.port.out.BreederPersistencePort;
import projet.uf.modules.breeder.application.port.out.CatteryPersistencePort;
import projet.uf.modules.breeder.domain.model.Breeder;
import projet.uf.modules.breeder.domain.model.Cattery;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BreederServiceTest {

    @Mock
    private BreederPersistencePort breederPersistencePort;

    @Mock
    private CatteryPersistencePort catteryPersistencePort;

    private BreederService breederService;

    private OperatorUser adminOperator;
    private OperatorUser regularOperator;

    @BeforeEach
    void setUp() {
        breederService = new BreederService(breederPersistencePort, catteryPersistencePort);
        adminOperator = new OperatorUser(1L, true);
        regularOperator = new OperatorUser(2L, false);
    }

    @Test
    void createContactBreeder_shouldThrowException_whenCatteryNotFound() {
        // Arrange
        CreateContactBreederCommand command = new CreateContactBreederCommand("Test Breeder", "123456789", "TEST", true, true, false);
        when(catteryPersistencePort.getById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, 
            () -> breederService.createContactBreeder(command, 1L, adminOperator));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(catteryPersistencePort).getById(1L);
        verify(breederPersistencePort, never()).save(any());
    }

    @Test
    void createContactBreeder_shouldThrowException_whenUnauthorized() {
        // Arrange
        CreateContactBreederCommand command = new CreateContactBreederCommand("Test Breeder", "123456789", "TEST", true, true, false);
        Cattery cattery = new Cattery(3L); // Created by user 3, not by regularOperator (id=2)
        cattery.setId(1L);

        when(catteryPersistencePort.getById(anyLong())).thenReturn(Optional.of(cattery));

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, 
            () -> breederService.createContactBreeder(command, 1L, regularOperator));
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
        verify(catteryPersistencePort).getById(1L);
        verify(breederPersistencePort, never()).save(any());
    }

    @Test
    void createContactBreeder_shouldThrowException_whenAffixAlreadyExists() {
        // Arrange
        CreateContactBreederCommand command = new CreateContactBreederCommand("Test Breeder", "123456789", "TEST", true, true, false);
        Cattery cattery = new Cattery(1L); // Created by adminOperator
        cattery.setId(1L);

        when(catteryPersistencePort.getById(anyLong())).thenReturn(Optional.of(cattery));
        when(breederPersistencePort.existsByAffixAndCatteryId(anyString(), anyLong())).thenReturn(true);

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, 
            () -> breederService.createContactBreeder(command, 1L, adminOperator));
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        verify(catteryPersistencePort).getById(1L);
        verify(breederPersistencePort).existsByAffixAndCatteryId("TEST", 1L);
        verify(breederPersistencePort, never()).save(any());
    }

    @Test
    void createContactBreeder_shouldCreateBreeder_whenValidData() {
        // Arrange
        CreateContactBreederCommand command = new CreateContactBreederCommand("Test Breeder", "123456789", "TEST", true, true, false);
        Cattery cattery = new Cattery(1L); // Created by adminOperator
        cattery.setId(1L);

        Breeder expectedBreeder = new Breeder(
            1L, "Test Breeder", "123456789", "TEST", true, null, null, 1L, true, false
        );

        when(catteryPersistencePort.getById(anyLong())).thenReturn(Optional.of(cattery));
        when(breederPersistencePort.existsByAffixAndCatteryId(anyString(), anyLong())).thenReturn(false);
        when(breederPersistencePort.save(any(Breeder.class))).thenReturn(expectedBreeder);

        // Act
        Breeder result = breederService.createContactBreeder(command, 1L, adminOperator);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Breeder", result.getName());
        assertEquals("TEST", result.getAffix());
        verify(catteryPersistencePort).getById(1L);
        verify(breederPersistencePort).existsByAffixAndCatteryId("TEST", 1L);
        verify(breederPersistencePort).save(any(Breeder.class));
    }

    @Test
    void createEmptyCatteryBreeder_shouldCreateBreeder() {
        // Arrange
        Breeder expectedBreeder = new Breeder("Test Cattery", 1L);
        expectedBreeder.setId(1L);

        when(breederPersistencePort.save(any(Breeder.class))).thenReturn(expectedBreeder);

        // Act
        Breeder result = breederService.createEmptyCatteryBreeder("Test Cattery", 1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Cattery", result.getName());
        verify(breederPersistencePort).save(any(Breeder.class));
    }

    @Test
    void getById_shouldThrowException_whenNotAdmin() {
        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, 
            () -> breederService.getById(1L, regularOperator));
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
        verify(breederPersistencePort, never()).getById(anyLong());
    }

    @Test
    void getById_shouldThrowException_whenBreederNotFound() {
        // Arrange
        when(breederPersistencePort.getById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, 
            () -> breederService.getById(1L, adminOperator));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(breederPersistencePort).getById(1L);
    }

    @Test
    void getById_shouldReturnBreeder_whenFound() {
        // Arrange
        Breeder expectedBreeder = new Breeder("Test Breeder", 1L);
        expectedBreeder.setId(1L);

        when(breederPersistencePort.getById(anyLong())).thenReturn(Optional.of(expectedBreeder));

        // Act
        Breeder result = breederService.getById(1L, adminOperator);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Breeder", result.getName());
        verify(breederPersistencePort).getById(1L);
    }

    @Test
    void getAll_shouldThrowException_whenNotAdmin() {
        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, 
            () -> breederService.getAll(regularOperator));
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
        verify(breederPersistencePort, never()).getAll();
    }

    @Test
    void getAll_shouldReturnAllBreeders() {
        // Arrange
        Breeder breeder1 = new Breeder("Breeder 1", 1L);
        breeder1.setId(1L);

        Breeder breeder2 = new Breeder("Breeder 2", 2L);
        breeder2.setId(2L);

        List<Breeder> expectedBreeders = Arrays.asList(breeder1, breeder2);

        when(breederPersistencePort.getAll()).thenReturn(expectedBreeders);

        // Act
        List<Breeder> result = breederService.getAll(adminOperator);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Breeder 1", result.get(0).getName());
        assertEquals("Breeder 2", result.get(1).getName());
        verify(breederPersistencePort).getAll();
    }

    @Test
    void deleteById_shouldThrowException_whenNotAdmin() {
        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, 
            () -> breederService.deleteById(1L, regularOperator));
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
        verify(breederPersistencePort, never()).getById(anyLong());
        verify(breederPersistencePort, never()).deleteById(anyLong());
    }

    @Test
    void deleteById_shouldThrowException_whenBreederNotFound() {
        // Arrange
        when(breederPersistencePort.getById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, 
            () -> breederService.deleteById(1L, adminOperator));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(breederPersistencePort).getById(1L);
        verify(breederPersistencePort, never()).deleteById(anyLong());
    }

    @Test
    void deleteById_shouldThrowException_whenBreederLinkedToCattery() {
        // Arrange
        Breeder breeder = new Breeder("Test Breeder", 1L);
        breeder.setId(1L);

        when(breederPersistencePort.getById(anyLong())).thenReturn(Optional.of(breeder));
        when(catteryPersistencePort.isBreederLinkedToAnyCattery(anyLong())).thenReturn(true);

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, 
            () -> breederService.deleteById(1L, adminOperator));
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        verify(breederPersistencePort).getById(1L);
        verify(catteryPersistencePort).isBreederLinkedToAnyCattery(1L);
        verify(breederPersistencePort, never()).deleteById(anyLong());
    }

    @Test
    void deleteById_shouldDeleteBreeder_whenValid() {
        // Arrange
        Breeder breeder = new Breeder("Test Breeder", 1L);
        breeder.setId(1L);

        when(breederPersistencePort.getById(anyLong())).thenReturn(Optional.of(breeder));
        when(catteryPersistencePort.isBreederLinkedToAnyCattery(anyLong())).thenReturn(false);

        // Act
        breederService.deleteById(1L, adminOperator);

        // Assert
        verify(breederPersistencePort).getById(1L);
        verify(catteryPersistencePort).isBreederLinkedToAnyCattery(1L);
        verify(breederPersistencePort).deleteById(1L);
    }

    @Test
    void updateCatteryBreeder_shouldThrowException_whenCatteryNotFound() {
        // Arrange
        UpdateCatteryBreederCommand command = new UpdateCatteryBreederCommand("Test Breeder", "123456789", "TEST", true, true, false);
        when(catteryPersistencePort.getById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, 
            () -> breederService.updateCatteryBreeder(1L, command, adminOperator));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(catteryPersistencePort).getById(1L);
        verify(breederPersistencePort, never()).save(any());
    }

    @Test
    void updateCatteryBreeder_shouldThrowException_whenUnauthorized() {
        // Arrange
        UpdateCatteryBreederCommand command = new UpdateCatteryBreederCommand("Test Breeder", "123456789", "TEST", true, true, false);
        Cattery cattery = new Cattery(3L); // Created by user 3, not by regularOperator (id=2)
        cattery.setId(1L);

        when(catteryPersistencePort.getById(anyLong())).thenReturn(Optional.of(cattery));

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, 
            () -> breederService.updateCatteryBreeder(1L, command, regularOperator));
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
        verify(catteryPersistencePort).getById(1L);
        verify(breederPersistencePort, never()).save(any());
    }

    @Test
    void updateCatteryBreeder_shouldCreateNewBreeder_whenNoExistingBreeder() {
        // Arrange
        UpdateCatteryBreederCommand command = new UpdateCatteryBreederCommand("Test Breeder", "123456789", "TEST", true, true, false);
        Cattery cattery = new Cattery(1L); // Created by adminOperator
        cattery.setId(1L);

        Breeder savedBreeder = new Breeder(
            1L, "Test Breeder", "123456789", "TEST", true, null, null, 1L, true, false
        );

        when(catteryPersistencePort.getById(anyLong())).thenReturn(Optional.of(cattery));
        when(breederPersistencePort.existsByAffixAndCatteryId(anyString(), anyLong())).thenReturn(false);
        when(breederPersistencePort.save(any(Breeder.class))).thenReturn(savedBreeder);

        // Act
        Breeder result = breederService.updateCatteryBreeder(1L, command, adminOperator);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Breeder", result.getName());
        verify(catteryPersistencePort).getById(1L);
        verify(breederPersistencePort).existsByAffixAndCatteryId("TEST", 1L);
        verify(breederPersistencePort).save(any(Breeder.class));
        verify(catteryPersistencePort).update(cattery);
    }

    @Test
    void updateCatteryBreeder_shouldUpdateExistingBreeder_whenBreederExists() {
        // Arrange
        UpdateCatteryBreederCommand command = new UpdateCatteryBreederCommand("Updated Breeder", "123456789", "TEST", true, true, false);
        Cattery cattery = new Cattery(1L); // Created by adminOperator
        cattery.setId(1L);
        cattery.setLinkedToBreederId(1L);

        Breeder existingBreeder = new Breeder("Test Breeder", 1L);
        existingBreeder.setId(1L);

        Breeder updatedBreeder = new Breeder(
            1L, "Updated Breeder", "123456789", "TEST", true, null, null, 1L, true, false
        );

        when(catteryPersistencePort.getById(anyLong())).thenReturn(Optional.of(cattery));
        when(breederPersistencePort.getById(anyLong())).thenReturn(Optional.of(existingBreeder));
        when(breederPersistencePort.existsByAffixAndCatteryIdExceptId(anyString(), anyLong(), anyLong())).thenReturn(false);
        when(breederPersistencePort.save(any(Breeder.class))).thenReturn(updatedBreeder);

        // Act
        Breeder result = breederService.updateCatteryBreeder(1L, command, adminOperator);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Updated Breeder", result.getName());
        verify(catteryPersistencePort).getById(1L);
        verify(breederPersistencePort).getById(1L);
        verify(breederPersistencePort).existsByAffixAndCatteryIdExceptId("TEST", 1L, 1L);
        verify(breederPersistencePort).save(any(Breeder.class));
        verify(catteryPersistencePort, never()).update(any());
    }
}
