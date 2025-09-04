package projet.uf.modules.cat.application;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.port.in.CatteryAuthorizationUseCase;
import projet.uf.modules.cat.application.command.CatCommand;
import projet.uf.modules.cat.application.command.LitterCommand;
import projet.uf.modules.cat.application.dto.CatDetailsDto;
import projet.uf.modules.cat.application.ports.in.CatAuthorizationUseCase;
import projet.uf.modules.cat.application.ports.in.CatCoatUseCase;
import projet.uf.modules.cat.application.ports.in.CreateLitterUseCase;
import projet.uf.modules.cat.application.ports.in.LitterAuthorizationUseCase;
import projet.uf.modules.cat.application.ports.out.CatPersistencePort;
import projet.uf.modules.cat.domain.model.Cat;
import projet.uf.modules.cat.domain.model.Litter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CatServiceTest {

    private final CatPersistencePort mockCatPersistencePort = mock(CatPersistencePort.class);
    private final CatteryAuthorizationUseCase mockCatteryAccessUseCase = mock(CatteryAuthorizationUseCase.class);
    private final CatAuthorizationUseCase mockCatAccessUseCase = mock(CatAuthorizationUseCase.class);
    private final CatCoatUseCase mockCatCoatUseCase = mock(CatCoatUseCase.class);
    private final CatDtoAssembler mockDtoAssembler = mock(CatDtoAssembler.class);
    private final LitterAuthorizationUseCase mockLitterAuthorizationUseCase = mock(LitterAuthorizationUseCase.class);
    private final CreateLitterUseCase mockCreateLitterUseCase = mock(CreateLitterUseCase.class);

    private final CatService catService = new CatService(
            mockCatPersistencePort,
            mockCatteryAccessUseCase,
            mockCatAccessUseCase,
            mockCatCoatUseCase,
            mockDtoAssembler,
            mockLitterAuthorizationUseCase,
            mockCreateLitterUseCase
    );

    @Test
    void shouldCreateCatSuccessfully() {
        Long catteryId = 1L;
        CatCommand command = new CatCommand("CatName", "Nickname", true, "P123", "I123", false, null, false, null, true, "Healthy cat", null, null, null);
        OperatorUser operator = new OperatorUser();
        Cat cat = new Cat();
        cat.setCreatedByCatteryId(catteryId);

        when(mockCatteryAccessUseCase.hasUserAccessToCattery(catteryId, operator)).thenReturn(true);
        when(mockCatPersistencePort.save(any(Cat.class))).thenReturn(cat);
        when(mockDtoAssembler.toDetailsDto(any(Cat.class))).thenReturn(new CatDetailsDto(1L, "CatName", "Nickname", true, null, null, "P123", "I123", false, null, false, null, "Healthy cat", catteryId));

        CatDetailsDto result = catService.createCat(command, catteryId, operator);

        assertNotNull(result);
        assertEquals("CatName", result.name());
        verify(mockCatteryAccessUseCase).hasUserAccessToCattery(catteryId, operator);
        verify(mockCatPersistencePort).save(any(Cat.class));
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotHaveAccessToCattery() {
        Long catteryId = 2L;
        CatCommand command = new CatCommand("CatName", "Nickname", true, "P123", "I123", false, null, false, null, true, "Healthy cat", null, null, null);
        OperatorUser operator = new OperatorUser();

        when(mockCatteryAccessUseCase.hasUserAccessToCattery(catteryId, operator)).thenReturn(false);

        ApiException exception = assertThrows(ApiException.class, () -> catService.createCat(command, catteryId, operator));

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
        verify(mockCatteryAccessUseCase).hasUserAccessToCattery(catteryId, operator);
        verifyNoInteractions(mockCatPersistencePort);
    }

    @Test
    void shouldThrowExceptionWhenBothLitterIdAndLitterArePresent() {
        Long catteryId = 1L;
        LitterCommand litter = mock(LitterCommand.class);
        CatCommand command = new CatCommand("CatName", "Nickname", true, "P123", "I123", false, null, false, null, true, "Healthy cat", 10L, litter, null);
        OperatorUser operator = new OperatorUser();

        when(mockCatteryAccessUseCase.hasUserAccessToCattery(catteryId, operator)).thenReturn(true);

        ApiException exception = assertThrows(ApiException.class, () -> catService.createCat(command, catteryId, operator));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Ne pas renseigner à la fois une portée existante et une nouvelle portée", exception.getMessage());
        verify(mockCatteryAccessUseCase).hasUserAccessToCattery(catteryId, operator);
        verifyNoInteractions(mockCatPersistencePort);
    }

    @Test
    void shouldThrowExceptionWhenLitterIsFromDifferentCattery() {
        Long catteryId = 1L;
        Litter litter = new Litter();
        litter.setCreatedByCatteryId(2L);
        CatCommand command = new CatCommand("CatName", "Nickname", true, "P123", "I123", false, null, false, null, true, "Healthy cat", 11L, null, null);
        OperatorUser operator = new OperatorUser();

        when(mockCatteryAccessUseCase.hasUserAccessToCattery(catteryId, operator)).thenReturn(true);
        when(mockLitterAuthorizationUseCase.getLitterOrThrow(11L, operator)).thenReturn(litter);

        ApiException exception = assertThrows(ApiException.class, () -> catService.createCat(command, catteryId, operator));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("La portée doit faire partie de la même chatterie que le chat", exception.getMessage());
        verify(mockCatteryAccessUseCase).hasUserAccessToCattery(catteryId, operator);
        verify(mockLitterAuthorizationUseCase).getLitterOrThrow(11L, operator);
        verifyNoInteractions(mockCatPersistencePort);
    }

    @Test
    void shouldAttachLitterToCat() {
        Long catteryId = 1L;
        LitterCommand litterCommand = new LitterCommand();
        Litter litter = new Litter();
        litter.setId(15L);
        Cat cat = new Cat();
        cat.setCreatedByCatteryId(catteryId);
        CatCommand command = new CatCommand("CatName", "Nickname", true, "P123", "I123", false, null, false, null, true, "Healthy cat", null, litterCommand, null);
        OperatorUser operator = new OperatorUser();

        when(mockCatteryAccessUseCase.hasUserAccessToCattery(catteryId, operator)).thenReturn(true);
        when(mockCreateLitterUseCase.createLitter(litterCommand, catteryId, operator)).thenReturn(litter);
        when(mockCatPersistencePort.save(any(Cat.class))).thenReturn(cat);
        when(mockDtoAssembler.toDetailsDto(any(Cat.class))).thenReturn(new CatDetailsDto(1L, "CatName", "Nickname", true, null, null, "P123", "I123", false, null, false, null, "Healthy cat", catteryId));

        CatDetailsDto result = catService.createCat(command, catteryId, operator);

        assertNotNull(result);
        assertEquals("CatName", result.name());
        assertEquals(catteryId, result.createdByCatteryId());
        verify(mockCatteryAccessUseCase).hasUserAccessToCattery(catteryId, operator);
        verify(mockCreateLitterUseCase).createLitter(litterCommand, catteryId, operator);
        verify(mockCatPersistencePort).save(any(Cat.class));
    }
}