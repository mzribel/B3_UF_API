package projet.uf.modules.auth.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projet.uf.modules.auth.adapters.out.security.JwtService;
import projet.uf.modules.auth.application.AuthService;
import projet.uf.modules.auth.application.ports.in.AuthUseCase;
import projet.uf.modules.auth.application.ports.out.PasswordEncoder;
import projet.uf.modules.user.application.port.out.UserPersistencePort;

@Configuration
public class AuthConfiguration {
    @Bean
    public AuthUseCase authService(
            PasswordEncoder passwordEncoder,
            UserPersistencePort userPersistencePort,
            JwtService jwtService) {
        return new AuthService(passwordEncoder, userPersistencePort, jwtService);
    }
}
