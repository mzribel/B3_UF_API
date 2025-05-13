package projet.uf.modules.template.adapters.in.rest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import projet.uf.modules.template.exceptions.TemplateException;
import projet.uf.modules.template.domain.model.Template;
import projet.uf.modules.template.application.service.TemplateUseCaseService;

import java.util.List;

@RestController
public class TemplateController {

    private final TemplateUseCaseService templateService;

    public TemplateController(final TemplateUseCaseService templateService) {
        this.templateService = templateService;
    }

    @GetMapping(value = "/template/{name}/{accurate}")
    public List<Template> getTemplateByName(@PathVariable final String name,
                                            @PathVariable final boolean accurate) throws TemplateException {
        return templateService.getTemplateByName(name, accurate);
    }

}
