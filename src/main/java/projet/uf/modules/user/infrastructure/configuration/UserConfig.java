package projet.uf.modules.user.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projet.uf.modules.user.adapter.out.persistence.JpaUserRepository;
import projet.uf.modules.user.adapter.out.persistence.UserPersistenceAdapter;
import projet.uf.modules.user.application.UserService;
import projet.uf.modules.user.application.port.out.UserPersistencePort;

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