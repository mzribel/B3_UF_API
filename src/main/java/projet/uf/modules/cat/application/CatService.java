package projet.uf.modules.cat.application;

import projet.uf.modules.cat.application.ports.in.*;
import projet.uf.modules.cat.application.ports.out.CatPersistencePort;
import projet.uf.modules.cat.domain.model.Cat;

import java.util.List;
import java.util.Optional;

public class CatService implements
        CatUseCase
{
    private final CatPersistencePort catPersistencePort;

    public CatService(CatPersistencePort catPersistencePort) {
        this.catPersistencePort = catPersistencePort;
    }

    @Override
    public Cat createCat(CreateCatCommand command) {
        Cat cat = CatCommandMapper.fromCreateCommand(command);
        return catPersistencePort.save(cat);
    }

    @Override
    public Optional<Cat> getById(Long id) {
        return catPersistencePort.getById(id);
    }

    @Override
    public List<Cat> getByCatteryId(Long id) {
        return catPersistencePort.getByCatteryId(id);
    }

    @Override
    public List<Cat> getAll() {
        return catPersistencePort.getAll();
    }


    @Override
    public Cat updateCat(Long id, CreateCatCommand command) throws Exception {
        catPersistencePort.getById(id).orElseThrow(() -> new Exception("Cat doesn't exist"));

        Cat updatedCat = CatCommandMapper.fromCreateCommand(command);
        updatedCat.setId(id);
        return catPersistencePort.save(updatedCat);
    }
}
