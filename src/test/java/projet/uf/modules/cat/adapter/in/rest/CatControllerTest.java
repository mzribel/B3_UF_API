package projet.uf.modules.cat.adapter.in.rest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.adapters.in.rest.security.CurrentUserProvider;
import projet.uf.modules.auth.adapters.in.rest.security.JwtAuthenticationFilter;
import projet.uf.modules.auth.application.model.CurrentUser;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.cat.application.command.CatCommand;
import projet.uf.modules.cat.application.dto.CatDetailsDto;
import projet.uf.modules.cat.application.dto.CatPedigreeDto;
import projet.uf.modules.cat.application.ports.in.CatUseCase;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(CatController.class)
public class CatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CatUseCase catUseCase;

    @MockitoBean
    private CurrentUserProvider currentUserProvider;

    @MockitoBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUpSecurity() {
        CurrentUser currentUser = new CurrentUser(1L, false);
        Authentication auth = new UsernamePasswordAuthenticationToken(
                currentUser,
                null,
                java.util.List.of() // ou les autorités nécessaires
        );
        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
    }

    @AfterEach
    void clearSecurity() {
        SecurityContextHolder.clearContext();
    }


    @Test
    void shouldCreateCatSuccessfully() throws Exception {
        CatCommand catCommand = new CatCommand(
                "Tom",
                "Tommy",
                true,
                "PED12345",
                "ID123456789",
                false,
                null,
                false,
                null,
                true,
                "Healthy",
                1L,
                null,
                null
        );

        CatDetailsDto catDetailsDto = new CatDetailsDto(
                1L,
                "Tom",
                "Tommy",
                true,
                null,
                null,
                "PED12345",
                "ID123456789",
                false,
                null,
                false,
                null,
                "Healthy",
                10L
        );

        when(currentUserProvider.getCurrentUser())
                .thenReturn(mock(CurrentUser.class));
        when(catUseCase.createCat(any(CatCommand.class), anyLong(), any(OperatorUser.class))).thenReturn(catDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/catteries/10/cats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Tom",
                                    "surname": "Tommy",
                                    "sex": true,
                                    "pedigreeNo": "PED12345",
                                    "identificationNo": "ID123456789",
                                    "isNeutered": false,
                                    "isDeceased": false,
                                    "isInCattery": true,
                                    "notes": "Healthy",
                                    "litterId": 1
                                }
                                """)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Tom"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("Tommy"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pedigreeNumber").value("PED12345"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.identificationNumber").value("ID123456789"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.notes").value("Healthy"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdByCatteryId").value(10L));
    }

    @Test
    void shouldReturnBadRequestWhenRequiredFieldsAreMissing() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/catteries/10/cats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "surname": "Tommy",
                                    "sex": true,
                                    "pedigreeNo": "PED12345"
                                }
                                """)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

//    @Test
//    void shouldReturnUnauthorizedWhenUserIsNotAuthenticated() throws Exception {
//        SecurityContextHolder.clearContext(); // Simulate no auth context
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/catteries/10/cats")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("""
//                                {
//                                    "name": "Tom",
//                                    "surname": "Tommy",
//                                    "sex": true,
//                                    "pedigreeNo": "PED12345",
//                                    "identificationNo": "ID123456789",
//                                    "isInCattery": true
//                                }
//                                """)
//                )
//                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
//    }

    @Test
    void shouldReturnErrorWhenCatServiceThrowsException() throws Exception {
        when(currentUserProvider.getCurrentUser()).thenReturn(mock(CurrentUser.class));
        when(catUseCase.createCat(any(CatCommand.class), anyLong(), any(OperatorUser.class)))
                .thenThrow(new ApiException("Invalid data provided", HttpStatus.BAD_REQUEST));

        mockMvc.perform(MockMvcRequestBuilders.post("/catteries/10/cats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Tom",
                                    "surname": "Tommy",
                                    "sex": true,
                                    "pedigreeNo": "PED12345",
                                    "identificationNo": "ID123456789",
                                    "notes": "Healthy"
                                }
                                """)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid data provided"));
    }

    @Test
    void shouldCreateCatWithAlternativeOptionalFields() throws Exception {
        CatCommand catCommand = new CatCommand(
                "Jerry",
                null,
                false,
                null,
                null,
                true,
                null,
                true,
                null,
                false,
                "Active cat",
                null,
                null,
                null
        );

        CatDetailsDto catDetailsDto = new CatDetailsDto(
                2L,
                "Jerry",
                null,
                false,
                null,
                null,
                null,
                null,
                true,
                null,
                true,
                null,
                "Active cat",
                5L
        );

        when(currentUserProvider.getCurrentUser()).thenReturn(mock(CurrentUser.class));
        when(catUseCase.createCat(any(CatCommand.class), anyLong(), any(OperatorUser.class))).thenReturn(catDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/catteries/5/cats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Jerry",
                                    "sex": false,
                                    "isNeutered": true,
                                    "isDeceased": true,
                                    "notes": "Active cat"
                                }
                                """)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jerry"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isFemale").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isDeceased").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isNeutered").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.notes").value("Active cat"));
    }

    @Test
    void shouldReturnBadRequestWhenNameIsEmpty() throws Exception {
        // utilisateur courant
        var cu = new CurrentUser(1L, false); // ou new OperatorUser(1L, false) selon ton provider
        when(currentUserProvider.getCurrentUser()).thenReturn(cu);

        var auth = new UsernamePasswordAuthenticationToken(cu, null, java.util.List.of());

        mockMvc.perform(MockMvcRequestBuilders.post("/catteries/10/cats")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication(auth))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "name": "",
                  "surname": "Tommy",
                  "sex": true,
                  "pedigreeNo": "PED12345",
                  "identificationNo": "ID123456789",
                  "isNeutered": false,
                  "isDeceased": false,
                  "isInCattery": true,
                  "notes": "Healthy",
                  "litterId": 1
                }
            """))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Optionnel: s'assurer que la logique métier n'est pas atteinte en cas d'erreur de validation
        org.mockito.Mockito.verifyNoInteractions(catUseCase);
    }

    @Test
    void shouldGetCatSuccessfully() throws Exception {
        CatDetailsDto catDetailsDto = new CatDetailsDto(
                1L,
                "Tom",
                "Tommy",
                true,
                null,
                null,
                "PED12345",
                "ID123456789",
                false,
                null,
                false,
                null,
                "Healthy",
                10L
        );

        when(currentUserProvider.getCurrentUser()).thenReturn(mock(CurrentUser.class));
        when(catUseCase.getById(anyLong(), any(OperatorUser.class)))
                .thenReturn(catDetailsDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/cats/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Tom"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("Tommy"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pedigreeNumber").value("PED12345"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.identificationNumber").value("ID123456789"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.notes").value("Healthy"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdByCatteryId").value(10L));
    }

    @Test
    void shouldReturnNotFoundForNonexistentCat() throws Exception {
        when(currentUserProvider.getCurrentUser()).thenReturn(mock(CurrentUser.class));
        when(catUseCase.getById(anyLong(), any(OperatorUser.class)))
                .thenThrow(new ApiException("Cat not found", HttpStatus.NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders.get("/cats/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldGetAllCatsSuccessfully() throws Exception {
        List<CatDetailsDto> cats = List.of(
                new CatDetailsDto(1L, "Tom", "Tommy", true, null, null, "PED12345", "ID123456789", false, null, false, null, "Healthy", 10L),
                new CatDetailsDto(2L, "Jerry", "Mouse", false, null, null, "PED56789", "ID987654321", false, null, false, null, "Active", 10L)
        );

        when(currentUserProvider.getCurrentUser()).thenReturn(mock(CurrentUser.class));
        when(catUseCase.getAll(any(OperatorUser.class))).thenReturn(cats);

        mockMvc.perform(MockMvcRequestBuilders.get("/cats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Tom"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].surname").value("Tommy"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Jerry"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].surname").value("Mouse"));
    }

    @Test
    void shouldReturnEmptyListWhenNoCatsAvailable() throws Exception {
        when(currentUserProvider.getCurrentUser()).thenReturn(mock(CurrentUser.class));
        when(catUseCase.getAll(any(OperatorUser.class))).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/cats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
    }

//    @Test
//    void shouldGetCatPedigreeSuccessfully() throws Exception {
//        CatPedigreeDto sire = new CatPedigreeDto(
//                2L, "Tom Sr.", false, null, null, null, null, null, "PID6789", "ID234567890"
//        );
//
//        CatPedigreeDto dam = new CatPedigreeDto(
//                3L, "Tommy's Mom", true, null, null, null, null, null, "PID9876", "ID987654321"
//        );
//
//        CatPedigreeDto catPedigreeDto = new CatPedigreeDto(
//                1L, "Tom", true, null, null, dam, sire, null, "PED12345", "ID123456789"
//        );
//
//        when(currentUserProvider.getCurrentUser()).thenReturn(mock(CurrentUser.class));
//        when(catUseCase.getPedigreeById(1L, any(OperatorUser.class))).thenReturn(catPedigreeDto);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/cats/1/pedigree")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Tom"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.pedigreeNumber").value("PED12345"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.identificationNumber").value("ID123456789"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.sire.name").value("Tom Sr."))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.dam.name").value("Tommy's Mom"));
//    }

    @Test
    void shouldReturnNotFoundForNonexistentCatPedigree() throws Exception {
        when(currentUserProvider.getCurrentUser()).thenReturn(mock(CurrentUser.class));
        when(catUseCase.getPedigreeById(anyLong(), any(OperatorUser.class)))
                .thenThrow(new ApiException("Cat pedigree not found", HttpStatus.NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders.get("/cats/999/pedigree")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

//    @Test
//    void shouldGetCatteryCatsSuccessfully() throws Exception {
//        List<CatDetailsDto> catteryCats = List.of(
//                new CatDetailsDto(3L, "Whiskers", "Fluffy", true, null, null, "PED90123", "ID654321987", false, null, false, null, "Calm", 20L),
//                new CatDetailsDto(4L, "Felix", null, false, null, null, "PED54321", "ID678901234", false, null, true, null, "Playful", 20L)
//        );
//
//        when(currentUserProvider.getCurrentUser()).thenReturn(mock(CurrentUser.class));
//        when(catUseCase.getByCatteryId(20L, any(OperatorUser.class))).thenReturn(catteryCats);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/catteries/20/cats")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(3L))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Whiskers"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].surname").value("Fluffy"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(4L))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Felix"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1].surname").doesNotExist());
//    }
//
//    @Test
//    void shouldReturnNotFoundForNonexistentCatteryCats() throws Exception {
//        when(currentUserProvider.getCurrentUser()).thenReturn(mock(CurrentUser.class));
//        when(catUseCase.getByCatteryId(999L, any(OperatorUser.class)))
//                .thenThrow(new ApiException("Cattery not found", HttpStatus.NOT_FOUND));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/catteries/999/cats")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNotFound())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Cattery not found"));
//    }
}