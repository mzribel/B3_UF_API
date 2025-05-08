package projet.uf.adapters.in.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import projet.uf.exceptions.TemplateException;
import projet.uf.domain.model.Template;
import projet.uf.application.service.TemplateService;

import java.util.List;

@RestController
public class TemplateController {

    private final TemplateService templateService;

    public TemplateController(final TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping(value = "/template/{name}/{accurate}")
    public List<Template> getTemplateByName(@PathVariable final String name,
                                            @PathVariable final boolean accurate) throws TemplateException {
        return templateService.getTemplateByName(name, accurate);
    }

}
