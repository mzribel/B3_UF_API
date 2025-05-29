package projet.uf.modules.template.adapter.in.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import projet.uf.modules.template.application.port.in.TemplateUseCase;
import projet.uf.modules.template.exception.TemplateException;
import projet.uf.modules.template.domain.model.Template;
import java.util.List;

@RestController
@AllArgsConstructor
public class TemplateController {
    private final TemplateUseCase templateUseCase;

    @GetMapping(value = {"/template/{name}/{accurate}/", "/template/{name}/{accurate}"})
    public List<Template> getTemplateByName(
            @PathVariable final String name,
            @PathVariable final boolean accurate) throws TemplateException {
        return templateUseCase.getTemplateByName(name, accurate);
    }
}
