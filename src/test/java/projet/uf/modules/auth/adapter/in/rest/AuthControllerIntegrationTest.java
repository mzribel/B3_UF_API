package projet.uf.modules.auth.adapter.in.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import projet.uf.exceptions.ExceptionControllerAdvice;
import projet.uf.modules.auth.application.ports.out.PasswordEncoder;
import projet.uf.modules.auth.exception.WeakPasswordException;
import projet.uf.modules.auth.infrastructure.configuration.AuthConfiguration;
import projet.uf.modules.user.adapter.out.persistence.JpaUserRepository;
import projet.uf.modules.user.adapter.out.persistence.UserEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@Import(AuthConfiguration.class)
@SpringBootTest(properties = "spring.profiles.active=test")
@ActiveProfiles("test")
public class AuthControllerIntegrationTest {
    @Autowired
    private JpaUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void globalExceptionHandler_shouldReturn400_forWeakPasswordException() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/auth/register");

        ExceptionControllerAdvice handler = new ExceptionControllerAdvice();
        WeakPasswordException ex = new WeakPasswordException("Password is too weak", HttpStatus.BAD_REQUEST);

        ResponseEntity<Object> response = handler.handleApiException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void login_shouldReturn400_whenEmailIsMissing() throws Exception {
        String json = """
            {
              "password": "StrongP@ss1"
            }
        """;

        mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isBadRequest());
    }

    @Test
    void login_shouldReturn400_whenBodyIsEmpty() throws Exception {
        mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(""))
            .andExpect(status().isBadRequest());
    }

    @Test
    void register_shouldReturn400_whenMissingFields() throws Exception {
        String json = """
            {
              "email": "test@test.fr"
            }
        """;

        mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isBadRequest());
    }

    @Test
    void register_shouldReturn415_whenWrongContentType() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .content("email=test@test.fr&password=1234"))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void register_shouldReturn201_withTokenAndUser() throws Exception {
        String json = """
        {
          "email": "test@test.fr",
          "password": "Soleil123€",
          "name": "Test"
        }
        """;

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.user.email").value("test@test.fr"));
    }

    @Test
    void register_shouldReturn409_whenEmailAlreadyUsed() throws Exception {
        // Inscription initiale
        String json = """
            {
              "email": "alreadyexists@test.fr",
              "password": "Soleil123€",
              "name": "Test"
            }
        """;

        mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isConflict());
    }

    @Test
    void login_shouldReturn200_withTokenAndUser() throws Exception {
        // Préparation d’un utilisateur en base
        String rawPassword = "Soleil123€";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        userRepository.save(new UserEntity("login@test.fr", encodedPassword, "Login Test"));

        String json = """
            {
              "email": "login@test.fr",
              "password": "Soleil123€"
            }
        """;

        mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").exists())
            .andExpect(jsonPath("$.user.email").value("login@test.fr"))
            .andExpect(jsonPath("$.user.displayName").value("Login Test"));
    }

    @Test
    void register_shouldReturn400_whenEmailIsInvalid() throws Exception {
        String json = """
            {
              "email": "invalid-email",
              "password": "Soleil123€",
              "name": "Test"
            }
        """;

        mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isBadRequest());
    }

    @Test
    void register_shouldReturn400_whenDisplayNameTooLong() throws Exception {
        String longName = "a".repeat(21);
        String json = String.format("""
            {
              "email": "test@test.fr",
              "password": "Soleil123€",
              "displayName": "%s"
            }
        """, longName);

        mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isBadRequest());
    }

}