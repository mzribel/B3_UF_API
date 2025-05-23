package projet.uf.modules.breeders.adapters.out.persistence.breeder;

import lombok.AllArgsConstructor;
import projet.uf.modules.breeders.application.ports.out.BreederPersistencePort;
import projet.uf.modules.breeders.domain.model.Breeder;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class BreederPersistenceAdapter implements BreederPersistencePort {

    private final JpaBreederRepository jpaBreederRepository;

    @Override
    public Optional<Breeder> getById(Long id) {
        return jpaBreederRepository.findById(id).map(BreederEntityMapper::toModel);
    }

    @Override
    public List<Breeder> getByCatteryId(Long id) {
        return jpaBreederRepository.findByCreatedByCatteryId(id)
            .stream()
            .map(BreederEntityMapper::toModel)
            .toList();
    }

    @Override
    public List<Breeder> getAll() {
        return jpaBreederRepository.findAll()
            .stream()
            .map(BreederEntityMapper::toModel)
            .toList();
    }

    @Override
    public boolean existsByAffixAndCatteryId(String affix, Long catteryId) {
        return jpaBreederRepository.existsByAffixAndCreatedByCatteryId(affix, catteryId);
    }

    @Override
    public Breeder save(Breeder breeder) {
        BreederEntity entity = BreederEntityMapper.toEntity(breeder);
        BreederEntity saved = jpaBreederRepository.save(entity);
        return BreederEntityMapper.toModel(saved);
    }

    @Override
    public void deleteById(Long id) {
        // TODO : condition de suppression
    }
}
