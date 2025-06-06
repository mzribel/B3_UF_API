package projet.uf.modules.auth.infrastructure.configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import projet.uf.modules.auth.adapters.in.rest.security.JwtAuthenticationFilter;
import projet.uf.modules.auth.adapters.out.security.JwtService;
import projet.uf.modules.auth.infrastructure.configuration.AuthSecurityConfig;
import projet.uf.modules.user.application.port.out.UserPersistencePort;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AuthSecurityConfigTest {

    @Mock
    private UserPersistencePort userPersistencePort;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthSecurityConfig authSecurityConfig;

    @Test
    void jwtAuthenticationFilter_shouldReturnFilterInstance() {
        // Act
        JwtAuthenticationFilter filter = authSecurityConfig.jwtAuthenticationFilter();

        // Assert
        assertNotNull(filter);
    }

    // Note: Testing the filterChain method is challenging because HttpSecurity is not easy to mock
    // In a real-world scenario, this would be better tested with an integration test
    // that verifies the security configuration works as expected
}