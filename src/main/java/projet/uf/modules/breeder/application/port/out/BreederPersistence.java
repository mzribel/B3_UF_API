package projet.uf.modules.breeder.application.port.out;

import projet.uf.modules.breeder.domain.model.Breeder;

import java.util.List;
import java.util.Optional;

public interface BreederPersistence {
    Optional<Breeder> getById(Long id);
    List<Breeder> getByCatteryId(Long id);
    List<Breeder> getAll();
    boolean existsByAffixAndCatteryId(String affix, Long catteryId);
    Breeder save(Breeder breeder);
    void deleteById(Long id);
}
