package projet.uf.dictionary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import projet.uf.exceptions.TemplateException;
import projet.uf.repositories.TemplateRepository;
import projet.uf.services.TemplateService;

import static org.mockito.Mockito.verify;

//@ExtendWith(MockitoExtension.class)
public class TemplateServiceTest {

    @Mock
    private TemplateRepository templateRepository;

    @InjectMocks
    private TemplateService templateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTemplateByName() throws TemplateException {
        // Given
        String name = "testName";
        boolean accurate = true;

        // When
        templateService.getTemplateByName(name, accurate);

        // Then
         verify(templateRepository).findByName(name);
    }

}