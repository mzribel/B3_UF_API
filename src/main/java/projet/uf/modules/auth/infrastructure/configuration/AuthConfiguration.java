package projet.uf.modules.auth.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projet.uf.modules.auth.adapters.out.security.JwtService;
import projet.uf.modules.auth.application.AuthServiceImpl;
import projet.uf.modules.auth.application.ports.in.AuthService;
import projet.uf.modules.auth.application.ports.out.LoadUserPort;
import projet.uf.modules.auth.application.ports.out.PasswordEncoderPort;
import projet.uf.modules.auth.application.ports.out.SaveUserPort;

@Configuration
public class AuthConfiguration {
    @Bean
    public AuthService authService(PasswordEncoderPort passwordEncoder, LoadUserPort loadUserPort, SaveUserPort saveUserPort, JwtService jwtService) {
        return new AuthServiceImpl(passwordEncoder, loadUserPort, saveUserPort, jwtService);
    }
}
