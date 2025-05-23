package projet.uf.modules.breeders.application.ports.out;

import projet.uf.modules.breeders.domain.model.Breeder;
import projet.uf.modules.loof_characteristics.domain.model.Breed;

import java.util.List;
import java.util.Optional;

public interface BreederPersistencePort {
    Optional<Breeder> getById(Long id);
    List<Breeder> getByCatteryId(Long id);
    List<Breeder> getAll();
    boolean existsByAffixAndCatteryId(String affix, Long catteryId);
    Breeder save(Breeder breeder);
    void deleteById(Long id);
}
