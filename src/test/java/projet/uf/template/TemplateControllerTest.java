//package projet.uf.dictionary;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
//import org.springframework.test.web.servlet.MockMvc;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import projet.uf.adapters.in.rest.controllers.TemplateController;
//
//import java.util.ArrayList;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//@Testcontainers
//public class TemplateControllerTest {
//
//    // TODO : modifier ce template avec le type de bdd qu'on aura mis en place pour faire des test en conteneurs docker
//
////    @Container
////    @ServiceConnection
////    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16.3");
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    TemplateController templateController;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
////    @Test
////    void should_return_an_empty_list_when_get_template_by_name_is_called() throws Exception {
////        mockMvc.perform(get("/template/name")
////                        .param("name", "name")
////                        .param("accurate", "true"))
////                .andExpect(status().isOk())
////                .andExpect(content().json(objectMapper.writeValueAsString(new ArrayList<>())));
////    }
//}
