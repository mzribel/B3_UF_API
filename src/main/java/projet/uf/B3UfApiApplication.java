package projet.uf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class B3UfApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(B3UfApiApplication.class, args);
	}

}
