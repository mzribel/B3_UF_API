package projet.uf.modules.cat.adapter.in.rest;

import org.springframework.web.bind.annotation.*;
import projet.uf.modules.cat.application.ports.in.CatUseCase;
import projet.uf.modules.cat.application.ports.in.CreateCatCommand;
import projet.uf.modules.cat.domain.model.Cat;

import java.util.List;

@RestController
public class CatController {
    final CatUseCase catService;

    public CatController(CatUseCase catService) {
        this.catService = catService;
    }


    @GetMapping({"/cats/", "/cats"})
    public List<Cat> getAllCats() {
        return catService.getAll();
    }

    @PostMapping({"/cats/", "/cats"})
    public Cat createCat(@RequestBody CreateCatCommand command) {
        return catService.createCat(command);
    }

    @GetMapping({"/cattery/{id}/cats/", "/cattery/{id}/cats"})
    public List<Cat> getCatteryCats(@PathVariable Long id) {
        return catService.getByCatteryId(id);
    }

    @PutMapping({"/cats/{id}/", "/cats/{id}"})
    public Cat updateCat(@PathVariable Long id, @RequestBody CreateCatCommand command) throws Exception {
        return catService.updateCat(id, command);
    }
}
