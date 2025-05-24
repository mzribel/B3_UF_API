package projet.uf.modules.auth.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projet.uf.modules.auth.application.AuthServiceImpl;
import projet.uf.modules.auth.application.ports.in.AuthService;
import projet.uf.modules.auth.application.ports.out.UserPersistence;
import projet.uf.modules.auth.application.ports.out.PasswordEncoder;

@Configuration
public class AuthConfiguration {
    @Bean
    public AuthService authService(PasswordEncoder passwordEncoder, UserPersistence userPersistence) {
        return new AuthServiceImpl(passwordEncoder, userPersistence);
    }
}
