package projet.uf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import projet.uf.modules.auth.infrastructure.configuration.JwtProperties;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(JwtProperties.class)
@EnableCaching
public class B3UfApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(B3UfApiApplication.class, args);
	}
}
