package projet.uf.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "UF API - Breeders API",
                version = "1.0.0",
                description = "Cette API permet de gérer les éleveurs, les chats, les portées et les utilisateurs.",
                contact = @Contact(name = "Marianne Corbel & Tony de Donato", email = "marianne.corbel@ynov.com")
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Serveur local")
        }
)
public class  OpenApiConfig {
}
