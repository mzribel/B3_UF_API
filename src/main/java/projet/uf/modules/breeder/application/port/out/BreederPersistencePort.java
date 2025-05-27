package projet.uf.modules.breeder.application.port.out;

import projet.uf.modules.breeder.domain.model.Breeder;

import java.util.List;
import java.util.Optional;

public interface BreederPersistencePort {
    Optional<Breeder> getById(Long id);
    List<Breeder> getByCatteryId(Long id);
    List<Breeder> getAll();
    boolean existsByAffixAndCatteryId(String affix, Long catteryId);
    boolean existsByAffixAndCatteryIdExceptId(String affix, Long catteryId, Long breederId);
    Breeder save(Breeder breeder);
    void deleteById(Long id);
}
