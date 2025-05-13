package projet.uf.modules.auth.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projet.uf.modules.auth.application.AuthService;
import projet.uf.modules.auth.application.ports.in.RegisterUseCase;
import projet.uf.modules.auth.application.ports.out.LoadUserPort;
import projet.uf.modules.auth.application.ports.out.PasswordEncoderPort;
import projet.uf.modules.auth.application.ports.out.SaveUserPort;

@Configuration
public class AuthConfiguration {
    @Bean
    public AuthService authService(PasswordEncoderPort passwordEncoder, LoadUserPort loadUserPort, SaveUserPort saveUserPort) {
        return new AuthService(passwordEncoder, loadUserPort, saveUserPort);
    }

    @Bean
    public RegisterUseCase registerUseCase(AuthService authService) {
        return authService;
    }
}
