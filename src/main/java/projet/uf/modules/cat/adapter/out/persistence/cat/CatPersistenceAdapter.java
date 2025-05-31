package projet.uf.modules.cat.adapter.out.persistence.cat;

import lombok.AllArgsConstructor;
import projet.uf.modules.cat.application.ports.out.CatPersistencePort;
import projet.uf.modules.cat.domain.model.Cat;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class CatPersistenceAdapter implements CatPersistencePort {
    private final JpaCatRepository jpaCatRepository;
    
    @Override
    public Optional<Cat> getById(Long id) {
        return jpaCatRepository.findById(id).map(CatEntity::toModel);
    }

    @Override
    public List<Cat> getByCatteryId(Long id) {
        return jpaCatRepository.findAllByCreatedByCatteryId(id).stream().map(CatEntity::toModel).toList();
    }

    @Override
    public List<Cat> getAll() {
        return jpaCatRepository.findAll().stream().map(CatEntity::toModel).toList();
    }

    @Override
    public Cat save(Cat cat) {
        CatEntity catEntity = CatEntity.toEntity(cat);
        if (cat.getId() != null) {
            catEntity.setId(cat.getId());
        }
        CatEntity savedCatEntity = jpaCatRepository.save(catEntity);
        return CatEntity.toModel(savedCatEntity);
    }

    @Override
    public void deleteById(Long id) {
        jpaCatRepository.deleteById(id);
    }

    @Override
    public List<Cat> getByLitterIdAndCatteryId(Long litterId, Long catteryId) {
        return jpaCatRepository.findAllByLitterIdAndCreatedByCatteryId(litterId, catteryId)
                .stream().map(CatEntity::toModel).toList();
    }
}
