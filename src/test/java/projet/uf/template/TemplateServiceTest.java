package projet.uf.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import projet.uf.modules.template.exceptions.TemplateException;
import projet.uf.modules.template.adapters.out.persistence.JpaTemplateRepository;
import projet.uf.modules.template.application.service.TemplateUseCaseService;

import static org.mockito.Mockito.verify;

//@ExtendWith(MockitoExtension.class)
public class TemplateServiceTest {

    @Mock
    private JpaTemplateRepository templateRepository;

    @InjectMocks
    private TemplateUseCaseService templateService;

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
         verify(templateRepository).findByName("name");
    }

}