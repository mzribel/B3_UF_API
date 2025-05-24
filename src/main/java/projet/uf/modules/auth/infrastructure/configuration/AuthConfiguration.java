package projet.uf.modules.auth.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projet.uf.modules.auth.application.AuthService;
import projet.uf.modules.auth.application.ports.in.AuthUseCase;
import projet.uf.modules.auth.application.ports.out.UserAccessPort;
import projet.uf.modules.auth.application.ports.out.PasswordEncoder;

@Configuration
public class AuthConfiguration {
    @Bean
    public AuthUseCase authService(PasswordEncoder passwordEncoder, UserAccessPort userPersistence) {
        return new AuthService(passwordEncoder, userPersistence);
    }
}
