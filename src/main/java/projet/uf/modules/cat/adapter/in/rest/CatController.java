package projet.uf.modules.cat.adapter.in.rest;

import org.springframework.web.bind.annotation.*;
import projet.uf.modules.cat.application.ports.in.CreateCatCommand;
import projet.uf.modules.cat.application.ports.in.CreateCatUseCase;
import projet.uf.modules.cat.application.ports.in.GetCatUseCase;
import projet.uf.modules.cat.application.ports.in.UpdateCatUseCase;
import projet.uf.modules.cat.domain.model.Cat;

import java.util.List;

@RestController
public class CatController {
    final GetCatUseCase getCatUseCase;
    final CreateCatUseCase createCatUseCase;
    final UpdateCatUseCase updateCatUseCase;
    public CatController(GetCatUseCase getCatUseCase, CreateCatUseCase createCatUseCase, UpdateCatUseCase updateCatUseCase) {
        this.getCatUseCase = getCatUseCase;
        this.createCatUseCase = createCatUseCase;
        this.updateCatUseCase = updateCatUseCase;
    }

    @GetMapping("/cats")
    public List<Cat> getAllCats() {
        return getCatUseCase.getAll();
    }

    @PostMapping("/cats")
    public Cat createCat(@RequestBody CreateCatCommand command) {
        return createCatUseCase.createCat(command);
    }

    @GetMapping("/cattery/{id}/cats")
    public List<Cat> getCatteryCats(@PathVariable Long id) {
        return getCatUseCase.getByCatteryId(id);
    }

    @PutMapping("/cats/{id}")
    public Cat updateCat(@PathVariable Long id, @RequestBody CreateCatCommand command) throws Exception {
        return updateCatUseCase.updateCat(id, command);
    }
}
