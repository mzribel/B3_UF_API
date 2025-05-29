package projet.uf.modules.breeder.adapter.out.persistence.cattery;

import lombok.AllArgsConstructor;
import projet.uf.modules.breeder.application.port.out.CatteryPersistencePort;
import projet.uf.modules.breeder.domain.model.Cattery;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class CatteryPersistenceAdapter implements CatteryPersistencePort {
    private final JpaCatteryRepository jpaCatteryRepository;

    @Override
    public Optional<Cattery> getById(Long id) {
        return jpaCatteryRepository.findById(id).map(CatteryEntityMapper::toModel);
    }

    @Override
    public List<Cattery> getByCreatedByUserId(Long id) {
        return jpaCatteryRepository.findByCreatedByUserId(id)
            .stream()
            .map(CatteryEntityMapper::toModel)
            .toList();
    }

    @Override
    public List<Cattery> getAll() {
        return jpaCatteryRepository.findAll()
            .stream()
            .map(CatteryEntityMapper::toModel)
            .toList();
    }

    @Override
    public Cattery insert(Cattery cattery) {
        CatteryEntity entity = CatteryEntityMapper.toEntity(cattery);
        CatteryEntity saved = jpaCatteryRepository.save(entity);
        return CatteryEntityMapper.toModel(saved);
    }

    // TODO : Vérifications
    @Override
    public Cattery update(Cattery cattery) {
        CatteryEntity entity = CatteryEntityMapper.toEntity(cattery);
        CatteryEntity saved = jpaCatteryRepository.save(entity);
        return CatteryEntityMapper.toModel(saved);
    }

    @Override
    public void deleteById(Long id) {
        jpaCatteryRepository.deleteById(id);
    }

    @Override
    public boolean isBreederLinkedToAnyCattery(Long breederId) {
        return jpaCatteryRepository.existsByLinkedToBreederId(breederId);
    }
}
