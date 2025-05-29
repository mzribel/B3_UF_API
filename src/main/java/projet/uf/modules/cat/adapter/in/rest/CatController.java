package projet.uf.modules.cat.adapter.in.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import projet.uf.modules.cat.application.ports.in.CatUseCase;
import projet.uf.modules.cat.application.ports.in.CreateCatCommand;
import projet.uf.modules.cat.domain.model.Cat;

import java.util.List;

@RestController
@AllArgsConstructor
public class CatController {
    final CatUseCase catUseCase;

    @GetMapping({"/cats/", "/cats"})
    public List<Cat> getAllCats() {
        return catUseCase.getAll();
    }

    @PostMapping({"/cats/", "/cats"})
    public Cat createCat(@RequestBody CreateCatCommand command) {
        return catUseCase.createCat(command);
    }

    @GetMapping({"/cattery/{id}/cats/", "/cattery/{id}/cats"})
    public List<Cat> getCatteryCats(@PathVariable Long id) {
        return catUseCase.getByCatteryId(id);
    }

    @PutMapping({"/cats/{id}/", "/cats/{id}"})
    public Cat updateCat(@PathVariable Long id, @RequestBody CreateCatCommand command) throws Exception {
        return catUseCase.updateCat(id, command);
    }
}
