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
        return jpaCatteryRepository.findById(id).map(CatteryEntity::toModel);
    }

    @Override
    public List<Cattery> getByCreatedByUserId(Long id) {
        return jpaCatteryRepository.findByCreatedByUserId(id)
            .stream()
            .map(CatteryEntity::toModel)
            .toList();
    }

    @Override
    public List<Cattery> getAll() {
        return jpaCatteryRepository.findAll()
            .stream()
            .map(CatteryEntity::toModel)
            .toList();
    }

    @Override
    public Cattery insert(Cattery cattery) {
        CatteryEntity entity = CatteryEntity.toEntity(cattery);
        CatteryEntity saved = jpaCatteryRepository.save(entity);
        return CatteryEntity.toModel(saved);
    }

    // TODO : Vérifications
    @Override
    public Cattery update(Cattery cattery) {
        CatteryEntity entity = CatteryEntity.toEntity(cattery);
        CatteryEntity saved = jpaCatteryRepository.save(entity);
        return CatteryEntity.toModel(saved);
    }

    @Override
    public void deleteById(Long id) {
        jpaCatteryRepository.deleteById(id);
    }

    @Override
    public boolean isUserAdminOfCattery(Long userId, Long catteryId) {
        return jpaCatteryRepository.existsByIdAndCreatedByUserId(catteryId, userId);
    }

    @Override
    public boolean isBreederLinkedToAnyCattery(Long breederId) {
        return jpaCatteryRepository.existsByLinkedToBreederId(breederId);
    }
}
