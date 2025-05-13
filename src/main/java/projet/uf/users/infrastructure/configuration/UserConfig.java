package projet.uf.users.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import projet.uf.users.adapters.out.persistence.JpaUserRepository;
import projet.uf.users.adapters.out.persistence.UserPersistenceAdapter;
import projet.uf.users.application.ports.out.UserPersistencePort;
import projet.uf.users.application.service.UserService;

@Configuration
public class UserConfig {

    @Bean
    public UserPersistencePort userPersistencePort(JpaUserRepository jpaUserRepository) {
        return new UserPersistenceAdapter(jpaUserRepository);
    }

    @Bean
    public UserService userService(UserPersistencePort userPersistencePort) {
        return new UserService(userPersistencePort);
    }
}