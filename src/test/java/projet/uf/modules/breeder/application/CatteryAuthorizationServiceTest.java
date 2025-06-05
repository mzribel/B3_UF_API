package projet.uf.modules.breeder.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.CatteryAuthorizationService;
import projet.uf.modules.breeder.application.port.out.CatteryPersistencePort;
import projet.uf.modules.breeder.application.port.out.CatteryUserPersistencePort;
import projet.uf.modules.breeder.domain.model.Cattery;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatteryAuthorizationServiceTest {

    @Mock
    private CatteryPersistencePort catteryPersistencePort;

    @Mock
    private CatteryUserPersistencePort catteryUserPersistencePort;

    private CatteryAuthorizationService catteryAuthorizationService;

    private OperatorUser adminOperator;
    private OperatorUser regularOperator;

    @BeforeEach
    void setUp() {
        catteryAuthorizationService = new CatteryAuthorizationService(catteryPersistencePort, catteryUserPersistencePort);
        adminOperator = new OperatorUser(1L, true);
        regularOperator = new OperatorUser(2L, false);
    }

    @Test
    void getCatteryOrThrow_shouldThrowException_whenCatteryNotFound() {
        // Arrange
        when(catteryPersistencePort.getById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, 
            () -> catteryAuthorizationService.getCatteryOrThrow(1L, adminOperator));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(catteryPersistencePort).getById(1L);
    }

    @Test
    void getCatteryOrThrow_shouldThrowException_whenUserIsNotAuthorized() {
        // Arrange
        Cattery cattery = new Cattery(3L); // Created by user 3, not by regularOperator (id=2)
        cattery.setId(1L);

        when(catteryPersistencePort.getById(anyLong())).thenReturn(Optional.of(cattery));
        when(catteryUserPersistencePort.isUserMemberOfCattery(anyLong(), anyLong())).thenReturn(false);

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, 
            () -> catteryAuthorizationService.getCatteryOrThrow(1L, regularOperator));
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
        verify(catteryPersistencePort).getById(1L);
        verify(catteryUserPersistencePort).isUserMemberOfCattery(1L, 2L);
    }

    @Test
    void getCatteryOrThrow_shouldReturnCattery_whenUserIsAdmin() {
        // Arrange
        Cattery cattery = new Cattery(3L);
        cattery.setId(1L);

        when(catteryPersistencePort.getById(anyLong())).thenReturn(Optional.of(cattery));

        // Act
        Cattery result = catteryAuthorizationService.getCatteryOrThrow(1L, adminOperator);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(catteryPersistencePort).getById(1L);
        verify(catteryUserPersistencePort, never()).isUserMemberOfCattery(anyLong(), anyLong());
    }

    @Test
    void getCatteryOrThrow_shouldReturnCattery_whenUserIsOwner() {
        // Arrange
        Cattery cattery = new Cattery(2L); // Created by regularOperator (id=2)
        cattery.setId(1L);

        when(catteryPersistencePort.getById(anyLong())).thenReturn(Optional.of(cattery));

        // Act
        Cattery result = catteryAuthorizationService.getCatteryOrThrow(1L, regularOperator);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(catteryPersistencePort).getById(1L);
        verify(catteryUserPersistencePort, never()).isUserMemberOfCattery(anyLong(), anyLong());
    }

    @Test
    void getCatteryOrThrow_shouldReturnCattery_whenUserIsMember() {
        // Arrange
        Cattery cattery = new Cattery(3L); // Created by user 3, not by regularOperator (id=2)
        cattery.setId(1L);

        when(catteryPersistencePort.getById(anyLong())).thenReturn(Optional.of(cattery));
        when(catteryUserPersistencePort.isUserMemberOfCattery(anyLong(), anyLong())).thenReturn(true);

        // Act
        Cattery result = catteryAuthorizationService.getCatteryOrThrow(1L, regularOperator);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(catteryPersistencePort).getById(1L);
        verify(catteryUserPersistencePort).isUserMemberOfCattery(1L, 2L);
    }

    @Test
    void hasUserAccessToCattery_shouldThrowException_whenCatteryNotFound() {
        // Arrange
        when(catteryPersistencePort.getById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, 
            () -> catteryAuthorizationService.hasUserAccessToCattery(1L, regularOperator));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(catteryPersistencePort).getById(1L);
    }

    @Test
    void hasUserAccessToCattery_shouldReturnTrue_whenUserIsAdmin() {
        // Arrange
        // No need to mock anything for admin

        // Act
        boolean result = catteryAuthorizationService.hasUserAccessToCattery(1L, adminOperator);

        // Assert
        assertTrue(result);
        verify(catteryPersistencePort, never()).getById(anyLong());
        verify(catteryPersistencePort, never()).isUserAdminOfCattery(anyLong(), anyLong());
        verify(catteryUserPersistencePort, never()).isUserMemberOfCattery(anyLong(), anyLong());
    }

    @Test
    void hasUserAccessToCattery_shouldReturnTrue_whenUserIsOwner() {
        // Arrange
        Cattery cattery = new Cattery(2L); // Created by regularOperator (id=2)
        cattery.setId(1L);

        when(catteryPersistencePort.getById(anyLong())).thenReturn(Optional.of(cattery));
        when(catteryPersistencePort.isUserAdminOfCattery(anyLong(), anyLong())).thenReturn(true);

        // Act
        boolean result = catteryAuthorizationService.hasUserAccessToCattery(1L, regularOperator);

        // Assert
        assertTrue(result);
        verify(catteryPersistencePort).getById(1L);
        verify(catteryPersistencePort).isUserAdminOfCattery(2L, 1L);
        verify(catteryUserPersistencePort, never()).isUserMemberOfCattery(anyLong(), anyLong());
    }

    @Test
    void hasUserAccessToCattery_shouldReturnTrue_whenUserIsMember() {
        // Arrange
        Cattery cattery = new Cattery(3L); // Created by user 3, not by regularOperator (id=2)
        cattery.setId(1L);

        when(catteryPersistencePort.getById(anyLong())).thenReturn(Optional.of(cattery));
        when(catteryPersistencePort.isUserAdminOfCattery(anyLong(), anyLong())).thenReturn(false);
        when(catteryUserPersistencePort.isUserMemberOfCattery(anyLong(), anyLong())).thenReturn(true);

        // Act
        boolean result = catteryAuthorizationService.hasUserAccessToCattery(1L, regularOperator);

        // Assert
        assertTrue(result);
        verify(catteryPersistencePort).getById(1L);
        verify(catteryPersistencePort).isUserAdminOfCattery(2L, 1L);
        verify(catteryUserPersistencePort).isUserMemberOfCattery(2L, 1L);
    }

    @Test
    void hasUserAccessToCattery_shouldReturnFalse_whenUserHasNoAccess() {
        // Arrange
        Cattery cattery = new Cattery(3L); // Created by user 3, not by regularOperator (id=2)
        cattery.setId(1L);

        when(catteryPersistencePort.getById(anyLong())).thenReturn(Optional.of(cattery));
        when(catteryPersistencePort.isUserAdminOfCattery(anyLong(), anyLong())).thenReturn(false);
        when(catteryUserPersistencePort.isUserMemberOfCattery(anyLong(), anyLong())).thenReturn(false);

        // Act
        boolean result = catteryAuthorizationService.hasUserAccessToCattery(1L, regularOperator);

        // Assert
        assertFalse(result);
        verify(catteryPersistencePort).getById(1L);
        verify(catteryPersistencePort).isUserAdminOfCattery(2L, 1L);
        verify(catteryUserPersistencePort).isUserMemberOfCattery(2L, 1L);
    }
}
