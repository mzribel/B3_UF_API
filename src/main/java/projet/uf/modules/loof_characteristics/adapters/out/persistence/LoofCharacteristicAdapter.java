package projet.uf.modules.loof_characteristics.adapters.out.persistence;

import projet.uf.modules.loof_characteristics.application.ports.out.LoofCharacteristicPersistencePort;
import projet.uf.modules.loof_characteristics.domain.model.ALoofCharacteristic;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LoofCharacteristicAdapter <
    T extends ALoofCharacteristic,
    E extends LoofCharacteristicEntity>
    implements LoofCharacteristicPersistencePort<T>
{

    private final JpaLoofCharacteristicRepository<E> repository;
    private final LoofCharacteristicEntityMapper<T,E> mapper;

    public LoofCharacteristicAdapter(JpaLoofCharacteristicRepository<E> repository, LoofCharacteristicEntityMapper<T, E> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public T save(T t) {
        E saved = repository.save(mapper.toEntity(t));
        return mapper.toModel(saved);
    }

    @Override
    public Optional<T> getById(long id) {
        return repository.findById(id).map(mapper::toModel);
    }

    @Override
    public List<T> getAll() {
        return repository.findAll().stream().map(mapper::toModel).collect(Collectors.toList());
    }

    @Override
    public List<T> getByCode(String code) {
        return repository.findByCode(code).stream().map(mapper::toModel).collect(Collectors.toList());
    }

    @Override
    public Optional<T> getByName(String name) {
        return repository.findByName(name).map(mapper::toModel);
    }

    @Override
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }
}
