package projet.uf;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import projet.uf.modules.auth.adapters.out.security.JwtService;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
@ActiveProfiles("test")
class SecurityWebTests {

    private static final String PROTECTED_URL = "/loof/characteristics/breeds"; // à adapter
    private static final String ADMIN_URL = "/loof/characteristics/breeds";         // à adapter

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JwtService jwtService;

    private String bearer(String token) {
        return "Bearer " + token;
    }

    private String userToken() {
        return jwtService.generateToken("user@test.local", Map.of(
                "userId", 123L,
                "admin", false
        ));
    }

    private String adminToken() {
        return jwtService.generateToken("admin@test.local", Map.of(
                "userId", 1L,
                "admin", true
        ));
    }

    @Test
    void shouldReturn401_whenNoToken() throws Exception {
        mockMvc.perform(get(PROTECTED_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn200_whenUserToken_onProtectedEndpoint() throws Exception {
        mockMvc.perform(get(PROTECTED_URL)
                        .header("Authorization", bearer(userToken())))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn403_whenUserToken_onAdminEndpoint() throws Exception {
        mockMvc.perform(post(ADMIN_URL)
                        .header("Authorization", bearer(userToken())))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturn200_whenAdminToken_onAdminEndpoint() throws Exception {
        mockMvc.perform(post(ADMIN_URL)
                        .header("Authorization", bearer(adminToken())))
                .andExpect(status().isOk());
    }
}
