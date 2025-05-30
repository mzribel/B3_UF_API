package projet.uf.modules.cat.adapter.out.persistence.litter;

import lombok.AllArgsConstructor;
import projet.uf.modules.cat.application.ports.out.LitterPersistencePort;
import projet.uf.modules.cat.domain.model.Litter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class LitterPersistenceAdapter implements LitterPersistencePort {
    private final JpaLitterRepository jpaLitterRepository;

    @Override
    public Optional<Litter> getById(Long litterId) {
        return jpaLitterRepository.findById(litterId).map(LitterEntity::toModel);
    }

    @Override
    public List<Litter> getByCatteryId(Long catteryId) {
        return jpaLitterRepository.findAllByCreatedByCatteryId(catteryId)
                .stream().map(LitterEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public List<Litter> getAll() {
        return jpaLitterRepository.findAll()
                .stream().map(LitterEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public Litter save(Litter litter) {
        LitterEntity litterEntity = LitterEntity.toEntity(litter);
        LitterEntity saved = jpaLitterRepository.save(litterEntity);
        return LitterEntity.toModel(saved);
    }

    @Override
    public void deleteById(Long litterId) {
        jpaLitterRepository.deleteById(litterId);
    }
}
