package projet.uf.modules.breeder.adapter.out.persistence.breeder;

import lombok.AllArgsConstructor;
import projet.uf.modules.breeder.application.port.out.BreederPersistencePort;
import projet.uf.modules.breeder.domain.model.Breeder;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class BreederPersistenceAdapter implements BreederPersistencePort {

    private final JpaBreederRepository jpaBreederRepository;

    @Override
    public Optional<Breeder> getById(Long id) {
        return jpaBreederRepository.findById(id).map(BreederEntity::toModel);
    }

    @Override
    public List<Breeder> getAll() {
        return jpaBreederRepository.findAll()
            .stream()
            .map(BreederEntity::toModel)
            .toList();
    }

    @Override
    public boolean existsByAffixAndCatteryId(String affix, Long catteryId) {
        return jpaBreederRepository.existsByAffixAndCreatedByCatteryId(affix, catteryId);
    }

    @Override
    public boolean existsByAffixAndCatteryIdExceptId(String affix, Long catteryId, Long breederId) {
        return jpaBreederRepository.existsByAffixAndCreatedByCatteryIdAndIdNot(affix, catteryId, breederId);
    }

    @Override
    public Breeder save(Breeder breeder) {
        BreederEntity entity = BreederEntity.toEntity(breeder);
        BreederEntity saved = jpaBreederRepository.save(entity);
        return BreederEntity.toModel(saved);
    }

    @Override
    public void deleteById(Long id) {
        jpaBreederRepository.deleteById(id);
    }

    @Override
    public List<Breeder> getContactsByCatteryId(Long catteryId) {
        return jpaBreederRepository.findAllContactsCreatedByCatteryId(catteryId)
            .stream()
            .map(BreederEntity::toModel)
            .toList();
    }

    @Override
    public Optional<Breeder> getBreederLinkedToCatteryId(Long catteryId) {
        return jpaBreederRepository.findBreederLinkedToCatteryId(catteryId)
            .map(BreederEntity::toModel);
    }
}
