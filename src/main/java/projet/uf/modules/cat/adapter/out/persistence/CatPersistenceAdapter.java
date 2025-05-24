package projet.uf.modules.cat.adapter.out.persistence;

import projet.uf.modules.cat.application.ports.out.CatPersistencePort;
import projet.uf.modules.cat.domain.model.Cat;

import java.util.List;
import java.util.Optional;

public class CatPersistenceAdapter implements CatPersistencePort {
    private final JpaCatRepository jpaCatRepository;

    public CatPersistenceAdapter(JpaCatRepository jpaCatRepository) {
        this.jpaCatRepository = jpaCatRepository;
    }

    @Override
    public Optional<Cat> getById(Long id) {
        return jpaCatRepository.findById(id).map(CatEntityMapper::toModel);
    }

    @Override
    public List<Cat> getByCatteryId(Long id) {
        return jpaCatRepository.findAllByCreatedByCatteryId(id).stream().map(CatEntityMapper::toModel).toList();
    }

    @Override
    public List<Cat> getAll() {
        return jpaCatRepository.findAll().stream().map(CatEntityMapper::toModel).toList();
    }

    @Override
    public Cat save(Cat cat) {
        CatEntity catEntity = CatEntityMapper.toEntity(cat);
        CatEntity savedCatEntity = jpaCatRepository.save(catEntity);
        return CatEntityMapper.toModel(savedCatEntity);
    }

    @Override
    public void deleteById(Long id) {
        jpaCatRepository.deleteById(id);
    }
}
