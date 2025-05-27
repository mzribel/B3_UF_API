package projet.uf.modules.breeder.application.port;

import lombok.AllArgsConstructor;
import projet.uf.modules.breeder.application.model.BreederCommandMapper;
import projet.uf.modules.breeder.application.port.in.BreederUseCase;
import projet.uf.modules.breeder.application.model.CreateBreederCommand;
import projet.uf.modules.breeder.application.port.out.BreederPersistencePort;
import projet.uf.modules.breeder.domain.model.Breeder;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class BreederService implements BreederUseCase {
    private final BreederPersistencePort breederPersistencePort;

    @Override
    public Breeder create(CreateBreederCommand command, Long createdByCatteryId) {
        if (breederPersistencePort.existsByAffixAndCatteryId(command.getAffix(), createdByCatteryId)) {
            throw new IllegalArgumentException("Cattery already has a breeder with this affix");
        }
        Breeder breeder = BreederCommandMapper.fromCreateCommand(command, createdByCatteryId);
        return breederPersistencePort.save(breeder);
    }

    @Override
    public Breeder initialize(String name, Long createdByCatteryId) {
        Breeder breeder = new Breeder(name, createdByCatteryId);
        return breederPersistencePort.save(breeder);
    }

    @Override
    public Optional<Breeder> getById(Long id) {
        return breederPersistencePort.getById(id);
    }

    @Override
    public List<Breeder> getAll() {
        return breederPersistencePort.getAll();
    }

    @Override
    public List<Breeder> getByCatteryId(Long id) {
        return breederPersistencePort.getByCatteryId(id);
    }

    @Override
    public Breeder update(Long id, CreateBreederCommand command) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        breederPersistencePort.deleteById(id);
    }
}
