package projet.uf.modules.auth.adapters.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean; // <— nouveau
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import projet.uf.modules.auth.adapters.in.rest.dto.AuthenticatedUserDto;
import projet.uf.modules.auth.adapters.in.rest.security.JwtAuthenticationFilter;
import projet.uf.modules.auth.adapters.out.security.JwtService;
import projet.uf.modules.auth.application.ports.in.AuthUseCase;
import projet.uf.modules.auth.application.ports.in.LoginCommand;
import projet.uf.modules.user.application.dto.UserDto;
import projet.uf.modules.user.application.port.out.UserPersistencePort;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthUseCase authUseCase;

    // ✅ Remplacez les dépendances non chargées par le slice Web
    @MockitoBean
    UserPersistencePort userPersistencePort;

    // Si votre controller ou la conf sécurité référence ces beans :
    @MockitoBean
    JwtAuthenticationFilter jwtAuthenticationFilter; // évite son instanciation réelle
    @MockitoBean
    AuthenticationManager authenticationManager;     // si injecté quelque part
    @MockitoBean
    JwtService jwtService;                            // si utilisé

    @Test
    void whenValidLoginCommand_thenReturnsAuthenticatedUserDto() throws Exception {
        AuthenticatedUserDto authenticatedUserDto = new AuthenticatedUserDto(
                new UserDto(1L, "John Doe", "john.doe@example.com"),
                "valid-token"
        );
        Mockito.when(authUseCase.login(any(LoginCommand.class))).thenReturn(authenticatedUserDto);

        LoginCommand loginCommand = new LoginCommand("john.doe@example.com", "password123");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginCommand)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.email").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.displayName").value("john.doe@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("valid-token"));
    }

    @Test
    void whenInvalidLoginCommand_thenReturnsBadRequest() throws Exception {
        LoginCommand loginCommand = new LoginCommand("", "password123");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginCommand)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenEmailNotRegistered_thenReturnsConflict() throws Exception {
        Mockito.when(authUseCase.login(any(LoginCommand.class)))
                .thenThrow(new IllegalArgumentException("Email not registered"));

        LoginCommand loginCommand = new LoginCommand("unknown@example.com", "password123");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginCommand)))
                .andExpect(status().isConflict());
    }
}