package projet.uf.modules.cat.infrastructure.configuration;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import projet.uf.modules.breeder.application.port.in.CatteryAuthorizationUseCase;
import projet.uf.modules.breeder.application.port.out.BreederPersistencePort;
import projet.uf.modules.cat.adapter.out.persistence.cat.JpaCatRepository;
import projet.uf.modules.cat.adapter.out.persistence.catcoat.JpaCatCoatRepository;
import projet.uf.modules.cat.application.CatAuthorizationService;
import projet.uf.modules.cat.application.CatCoatService;
import projet.uf.modules.cat.application.CatDtoAssembler;
import projet.uf.modules.cat.application.CatService;
import projet.uf.modules.cat.application.ports.in.CatAuthorizationUseCase;
import projet.uf.modules.cat.application.ports.in.CatCoatUseCase;
import projet.uf.modules.cat.application.ports.in.CreateLitterUseCase;
import projet.uf.modules.cat.application.ports.in.LitterAuthorizationUseCase;
import projet.uf.modules.cat.application.ports.out.CatCoatPersistencePort;
import projet.uf.modules.cat.application.ports.out.CatPersistencePort;
import projet.uf.modules.cat.application.ports.out.LitterPersistencePort;
import projet.uf.modules.loof_characteristic.application.port.in.AllLoofCharacteristicsUseCase;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = CatConfig.class)
public class CatConfigTest {

    @MockitoBean
    private JpaCatRepository jpaCatRepository;

    @MockitoBean
    private JpaCatCoatRepository jpaCatCoatRepository;

    @MockitoBean
    private CatteryAuthorizationUseCase catteryAuthorizationUseCase;

    @MockitoBean
    private BreederPersistencePort breederPersistencePort;

    @MockitoBean
    private AllLoofCharacteristicsUseCase allLoofCharacteristicsUseCase;

    @MockitoBean
    private CatCoatUseCase catCoatUseCase;

    @MockitoBean
    private LitterPersistencePort litterPersistencePort;

    @MockitoBean
    private CatAuthorizationUseCase catAuthorizationUseCase;

    @MockitoBean
    private LitterAuthorizationUseCase litterAuthorizationUseCase;

    @MockitoBean
    private CreateLitterUseCase createLitterUseCase;

    @Autowired
    private CatConfig catConfig;

    @Test
    void testCatPersistencePortBean() {
        CatPersistencePort result = catConfig.catPersistencePort(jpaCatRepository);
        assertNotNull(result);
    }

    @Test
    void testCatCoatPersistencePortBean() {
        CatCoatPersistencePort result = catConfig.catCoatPersistencePort(jpaCatCoatRepository);
        assertNotNull(result);
    }

    @Test
    void testCatAuthorizationServiceBean() {
        CatAuthorizationService result = catConfig.catAccessService(Mockito.mock(CatPersistencePort.class), catteryAuthorizationUseCase);
        assertNotNull(result);
    }

    @Test
    void testCatCoatServiceBean() {
        CatCoatService result = catConfig.catCoatService(Mockito.mock(CatCoatPersistencePort.class), Mockito.mock(CatPersistencePort.class), allLoofCharacteristicsUseCase);
        assertNotNull(result);
    }

    @Test
    void testCatDtoAssemblerBean() {
        CatDtoAssembler result = catConfig.catDtoAssembler(breederPersistencePort, catCoatUseCase, litterPersistencePort, Mockito.mock(CatPersistencePort.class));
        assertNotNull(result);
    }

    @Test
    void testCatServiceBean() {
        CatService result = catConfig.catService(
                Mockito.mock(CatPersistencePort.class),
                catteryAuthorizationUseCase,
                Mockito.mock(CatDtoAssembler.class),
                catAuthorizationUseCase,
                catCoatUseCase,
                litterAuthorizationUseCase,
                createLitterUseCase
        );
        assertNotNull(result);
    }
}