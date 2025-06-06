package projet.uf.modules.breeder.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.CatteryDtoAssembler;
import projet.uf.modules.breeder.application.CatteryService;
import projet.uf.modules.breeder.application.command.AddUserToCatteryCommand;
import projet.uf.modules.breeder.application.command.CreateCatteryCommand;
import projet.uf.modules.breeder.application.dto.CatteryDetailsDto;
import projet.uf.modules.breeder.application.dto.UserCatteriesDto;
import projet.uf.modules.breeder.application.port.in.BreederUseCase;
import projet.uf.modules.breeder.application.port.in.CatteryAuthorizationUseCase;
import projet.uf.modules.breeder.application.port.out.CatteryPersistencePort;
import projet.uf.modules.breeder.application.port.out.CatteryUserPersistencePort;
import projet.uf.modules.breeder.domain.model.Breeder;
import projet.uf.modules.breeder.domain.model.Cattery;
import projet.uf.modules.breeder.domain.model.CatteryUser;
import projet.uf.modules.user.application.port.out.UserPersistencePort;
import projet.uf.modules.user.domain.model.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatteryServiceTest {

    @Mock
    private CatteryPersistencePort catteryPersistencePort;

    @Mock
    private CatteryUserPersistencePort catteryUserPersistencePort;

    @Mock
    private BreederUseCase breederUseCase;

    @Mock
    private UserPersistencePort userPersistencePort;

    @Mock
    private CatteryDtoAssembler dtoAssembler;

    @Mock
    private CatteryAuthorizationUseCase catteryAuthorizationUseCase;

    private CatteryService catteryService;

    private OperatorUser adminOperator;
    private OperatorUser regularOperator;

    @BeforeEach
    void setUp() {
        catteryService = new CatteryService(
                catteryPersistencePort,
                catteryUserPersistencePort,
                breederUseCase,
                userPersistencePort,
                dtoAssembler,
                catteryAuthorizationUseCase
        );
        adminOperator = new OperatorUser(1L, true);
        regularOperator = new OperatorUser(2L, false);
    }

    @Test
    void create_shouldThrowException_whenForbidden() {
        // Arrange
        CreateCatteryCommand command = new CreateCatteryCommand("Test Cattery", 3L);

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, 
            () -> catteryService.create(command, regularOperator));
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
        verify(userPersistencePort, never()).getById(anyLong());
    }

    @Test
    void create_shouldThrowException_whenUserNotFound() {
        // Arrange
        CreateCatteryCommand command = new CreateCatteryCommand("Test Cattery", 1L);
        when(userPersistencePort.getById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, 
            () -> catteryService.create(command, adminOperator));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(userPersistencePort).getById(1L);
        verify(catteryPersistencePort, never()).insert(any());
    }

    @Test
    void create_shouldCreateCattery_whenValidData() {
        // Arrange
        CreateCatteryCommand command = new CreateCatteryCommand("Test Cattery", 1L);
        User user = new User(1L, "test@example.com", "password", "Test User", false);
        Cattery newCattery = new Cattery(1L);
        newCattery.setId(1L);
        Breeder breeder = new Breeder("Test Cattery", 1L);
        breeder.setId(1L);
        CatteryDetailsDto expectedDto = new CatteryDetailsDto(1L, null, null, Collections.emptyList());

        when(userPersistencePort.getById(anyLong())).thenReturn(Optional.of(user));
        when(catteryPersistencePort.insert(any(Cattery.class))).thenReturn(newCattery);
        when(breederUseCase.createEmptyCatteryBreeder(anyString(), anyLong())).thenReturn(breeder);
        when(dtoAssembler.toDetailsDto(any(Cattery.class))).thenReturn(expectedDto);

        // Act
        CatteryDetailsDto result = catteryService.create(command, adminOperator);

        // Assert
        assertNotNull(result);
        verify(userPersistencePort).getById(1L);
        verify(catteryPersistencePort).insert(any(Cattery.class));
        verify(breederUseCase).createEmptyCatteryBreeder("Test Cattery", 1L);
        verify(catteryPersistencePort).update(newCattery);
        verify(dtoAssembler).toDetailsDto(newCattery);
    }

    @Test
    void getById_shouldReturnCatteryDetails() {
        // Arrange
        Cattery cattery = new Cattery(1L);
        cattery.setId(1L);
        CatteryDetailsDto expectedDto = new CatteryDetailsDto(1L, null, null, Collections.emptyList());

        when(catteryAuthorizationUseCase.getCatteryOrThrow(anyLong(), any(OperatorUser.class))).thenReturn(cattery);
        when(dtoAssembler.toDetailsDto(any(Cattery.class))).thenReturn(expectedDto);

        // Act
        CatteryDetailsDto result = catteryService.getById(1L, adminOperator);

        // Assert
        assertNotNull(result);
        verify(catteryAuthorizationUseCase).getCatteryOrThrow(1L, adminOperator);
        verify(dtoAssembler).toDetailsDto(cattery);
    }

    @Test
    void getAll_shouldReturnAllCatteries_whenAdmin() {
        // Arrange
        Cattery cattery1 = new Cattery(1L);
        cattery1.setId(1L);
        Cattery cattery2 = new Cattery(2L);
        cattery2.setId(2L);
        List<Cattery> catteries = Arrays.asList(cattery1, cattery2);

        CatteryDetailsDto dto1 = new CatteryDetailsDto(1L, null, null, Collections.emptyList());
        CatteryDetailsDto dto2 = new CatteryDetailsDto(2L, null, null, Collections.emptyList());

        when(catteryPersistencePort.getAll()).thenReturn(catteries);
        when(dtoAssembler.toDetailsDto(cattery1)).thenReturn(dto1);
        when(dtoAssembler.toDetailsDto(cattery2)).thenReturn(dto2);

        // Act
        List<CatteryDetailsDto> result = catteryService.getAll(adminOperator);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(catteryPersistencePort).getAll();
        verify(dtoAssembler, times(2)).toDetailsDto(any(Cattery.class));
    }

    @Test
    void deleteById_shouldDeleteCattery() {
        // Arrange
        Cattery cattery = new Cattery(1L);
        cattery.setId(1L);

        when(catteryAuthorizationUseCase.getCatteryOrThrow(anyLong(), any(OperatorUser.class))).thenReturn(cattery);

        // Act
        catteryService.deleteById(1L, adminOperator);

        // Assert
        verify(catteryAuthorizationUseCase).getCatteryOrThrow(1L, adminOperator);
        verify(catteryPersistencePort).deleteById(1L);
    }

    @Test
    void addUserToCattery_shouldThrowException_whenUserNotFound() {
        // Arrange
        Cattery cattery = new Cattery(1L);
        cattery.setId(1L);
        AddUserToCatteryCommand command = new AddUserToCatteryCommand(null, "nonexistent@example.com");

        when(catteryAuthorizationUseCase.getCatteryOrThrow(anyLong(), any(OperatorUser.class))).thenReturn(cattery);
        when(userPersistencePort.getByEmail(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, 
            () -> catteryService.addUserToCattery(1L, command, adminOperator));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(catteryAuthorizationUseCase).getCatteryOrThrow(1L, adminOperator);
        verify(userPersistencePort).getByEmail("nonexistent@example.com");
        verify(catteryUserPersistencePort, never()).addUserToCattery(anyLong(), anyLong());
    }

    @Test
    void addUserToCattery_shouldThrowException_whenUserIsAlreadyAdmin() {
        // Arrange
        Cattery cattery = new Cattery(2L);
        cattery.setId(1L);
        AddUserToCatteryCommand command = new AddUserToCatteryCommand(2L, null);
        User user = new User(2L, "admin@example.com", "password", "Admin User", false);

        when(catteryAuthorizationUseCase.getCatteryOrThrow(anyLong(), any(OperatorUser.class))).thenReturn(cattery);
        when(userPersistencePort.getById(anyLong())).thenReturn(Optional.of(user));

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, 
            () -> catteryService.addUserToCattery(1L, command, adminOperator));
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        verify(catteryAuthorizationUseCase).getCatteryOrThrow(1L, adminOperator);
        verify(userPersistencePort).getById(2L);
        verify(catteryUserPersistencePort, never()).addUserToCattery(anyLong(), anyLong());
    }

    @Test
    void addUserToCattery_shouldThrowException_whenUserIsAlreadyMember() {
        // Arrange
        Cattery cattery = new Cattery(1L);
        cattery.setId(1L);
        AddUserToCatteryCommand command = new AddUserToCatteryCommand(2L, null);
        User user = new User(2L, "member@example.com", "password", "Member User", false);

        when(catteryAuthorizationUseCase.getCatteryOrThrow(anyLong(), any(OperatorUser.class))).thenReturn(cattery);
        when(userPersistencePort.getById(anyLong())).thenReturn(Optional.of(user));
        when(catteryUserPersistencePort.isUserMemberOfCattery(anyLong(), anyLong())).thenReturn(true);

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, 
            () -> catteryService.addUserToCattery(1L, command, adminOperator));
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        verify(catteryAuthorizationUseCase).getCatteryOrThrow(1L, adminOperator);
        verify(userPersistencePort).getById(2L);
        verify(catteryUserPersistencePort).isUserMemberOfCattery(2L, 1L);
        verify(catteryUserPersistencePort, never()).addUserToCattery(anyLong(), anyLong());
    }

    @Test
    void addUserToCattery_shouldAddUser_whenValid() {
        // Arrange
        Cattery cattery = new Cattery(1L);
        cattery.setId(1L);
        AddUserToCatteryCommand command = new AddUserToCatteryCommand(2L, null);
        User user = new User(2L, "newmember@example.com", "password", "New Member", false);

        when(catteryAuthorizationUseCase.getCatteryOrThrow(anyLong(), any(OperatorUser.class))).thenReturn(cattery);
        when(userPersistencePort.getById(anyLong())).thenReturn(Optional.of(user));
        when(catteryUserPersistencePort.isUserMemberOfCattery(anyLong(), anyLong())).thenReturn(false);

        // Act
        catteryService.addUserToCattery(1L, command, adminOperator);

        // Assert
        verify(catteryAuthorizationUseCase).getCatteryOrThrow(1L, adminOperator);
        verify(userPersistencePort).getById(2L);
        verify(catteryUserPersistencePort).isUserMemberOfCattery(2L, 1L);
        verify(catteryUserPersistencePort).addUserToCattery(1L, 2L);
    }

    @Test
    void getUserCatteries_shouldThrowException_whenForbidden() {
        // Arrange
        Long userId = 3L;

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, 
            () -> catteryService.getUserCatteries(userId, regularOperator));
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
        verify(userPersistencePort, never()).existsById(anyLong());
    }

    @Test
    void getUserCatteries_shouldThrowException_whenUserNotFound() {
        // Arrange
        Long userId = 3L;
        when(userPersistencePort.existsById(anyLong())).thenReturn(false);

        // Act & Assert
        ApiException exception = assertThrows(ApiException.class, 
            () -> catteryService.getUserCatteries(userId, adminOperator));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(userPersistencePort).existsById(3L);
        verify(catteryPersistencePort, never()).getByCreatedByUserId(anyLong());
    }

    @Test
    void getUserCatteries_shouldReturnUserCatteries() {
        // Arrange
        Long userId = 1L;
        Cattery adminCattery = new Cattery(1L);
        adminCattery.setId(1L);
        Cattery memberCattery = new Cattery(2L);
        memberCattery.setId(2L);

        CatteryDetailsDto adminDto = new CatteryDetailsDto(1L, null, null, Collections.emptyList());
        CatteryDetailsDto memberDto = new CatteryDetailsDto(2L, null, null, Collections.emptyList());

        when(userPersistencePort.existsById(anyLong())).thenReturn(true);
        when(catteryPersistencePort.getByCreatedByUserId(anyLong())).thenReturn(Collections.singletonList(adminCattery));
        when(catteryUserPersistencePort.getAllCatteriesByUserId(anyLong())).thenReturn(Collections.singletonList(memberCattery));
        when(dtoAssembler.toDetailsDto(adminCattery)).thenReturn(adminDto);
        when(dtoAssembler.toDetailsDto(memberCattery)).thenReturn(memberDto);

        // Act
        UserCatteriesDto result = catteryService.getUserCatteries(userId, adminOperator);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.adminOf().size());
        assertEquals(1, result.memberOf().size());
        verify(userPersistencePort).existsById(1L);
        verify(catteryPersistencePort).getByCreatedByUserId(1L);
        verify(catteryUserPersistencePort).getAllCatteriesByUserId(1L);
        verify(dtoAssembler, times(2)).toDetailsDto(any(Cattery.class));
    }
}
