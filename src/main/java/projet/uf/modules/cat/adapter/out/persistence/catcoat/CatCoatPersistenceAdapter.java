package projet.uf.modules.cat.adapter.out.persistence.catcoat;

import lombok.AllArgsConstructor;
import projet.uf.modules.cat.application.ports.out.CatCoatPersistencePort;
import projet.uf.modules.cat.domain.model.CatCoat;

import java.util.Optional;

@AllArgsConstructor
public class CatCoatPersistenceAdapter implements CatCoatPersistencePort {
    private final JpaCatCoatRepository jpaCatCoatRepository;

    @Override
    public Optional<CatCoat> getByCatId(Long id) {
        return jpaCatCoatRepository.findByCatId(id).map(CatCoatEntityMapper::toModel);
    }

    public void deleteByCatId(Long id) {
        jpaCatCoatRepository.deleteById(id);
    }

    @Override
    public CatCoat save(CatCoat catCoat) {
        CatCoatEntity catCoatEntity = CatCoatEntityMapper.toEntity(catCoat);
        if (catCoat.getCatId() != null) {
            catCoatEntity.setCatId(catCoat.getCatId());
        }
        return CatCoatEntityMapper.toModel(jpaCatCoatRepository.save(catCoatEntity));
    }
}
