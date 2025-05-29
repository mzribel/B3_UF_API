package projet.uf.modules.cat.application.ports.out;

import projet.uf.modules.cat.domain.model.CatCoat;

import java.util.Optional;

public interface CatCoatPersistencePort {
    Optional<CatCoat> getByCatId(Long id);
    CatCoat save(CatCoat catCoat);
}
