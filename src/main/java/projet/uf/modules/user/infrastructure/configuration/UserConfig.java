package projet.uf.modules.user.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projet.uf.modules.user.adapter.out.persistence.JpaUserRepository;
import projet.uf.modules.user.adapter.out.persistence.UserPersistenceImpl;
import projet.uf.modules.user.application.UserServiceImpl;
import projet.uf.modules.user.application.port.out.UserPersistence;

@Configuration
public class UserConfig {
    @Bean
    public UserPersistence userPersistencePort(JpaUserRepository jpaUserRepository) {
        return new UserPersistenceImpl(jpaUserRepository);
    }

    @Bean
    public UserServiceImpl userService(UserPersistence userPersistencePort) {
        return new UserServiceImpl(userPersistencePort);
    }
}