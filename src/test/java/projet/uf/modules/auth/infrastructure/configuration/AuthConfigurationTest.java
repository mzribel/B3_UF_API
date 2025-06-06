package projet.uf.modules.auth.infrastructure.configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projet.uf.modules.auth.adapters.out.security.JwtService;
import projet.uf.modules.auth.application.AuthService;
import projet.uf.modules.auth.application.ports.in.AuthUseCase;
import projet.uf.modules.auth.application.ports.out.PasswordEncoder;
import projet.uf.modules.auth.infrastructure.configuration.AuthConfiguration;
import projet.uf.modules.user.application.port.out.UserPersistencePort;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class AuthConfigurationTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserPersistencePort userPersistencePort;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthConfiguration authConfiguration;

    @Test
    void authService_shouldReturnAuthServiceInstance() {
        // Act
        AuthUseCase authUseCase = authConfiguration.authService(passwordEncoder, userPersistencePort, jwtService);

        // Assert
        assertNotNull(authUseCase);
        assertTrue(authUseCase instanceof AuthService);
    }
}