package projet.uf.modules.loof_characteristic.adapter.out.persistence;

import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.loof_characteristic.application.port.out.LoofCharacteristicPersistencePort;
import projet.uf.modules.loof_characteristic.domain.model.ALoofCharacteristic;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LoofCharacteristicPersistenceAdapter<
    T extends ALoofCharacteristic,
    E extends LoofCharacteristicEntity>
    implements LoofCharacteristicPersistencePort<T>
{

    private final JpaLoofCharacteristicRepository<E> repository;
    private final LoofCharacteristicEntityMapper<T,E> mapper;

    public LoofCharacteristicPersistenceAdapter(JpaLoofCharacteristicRepository<E> repository, LoofCharacteristicEntityMapper<T, E> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public T insert(T t) {
        if (t.getId() != null) {
            throw new IllegalArgumentException("insert() ne doit pas être appelé avec un ID déjà défini");
        }

        E entity = mapper.toEntity(t);
        E saved = repository.save(entity);
        return mapper.toModel(saved);
    }

    public T update(T t) {
        if (t.getId() == null) {
            throw new IllegalArgumentException("update() nécessite un ID");
        }

        E existing = repository.findById(t.getId())
                .orElseThrow(() -> new ApiException("Aucune caractéristique trouvée avec l'id " + t.getId(), HttpStatus.NOT_FOUND));

        existing.setName(t.getName());
        existing.setCode(t.getCode());
        existing.setDetails(t.getDetails());

        E saved = repository.save(existing);
        return mapper.toModel(saved);
    }

    @Override
    public Optional<T> getById(long id) {
        return repository.findById(id).map(mapper::toModel);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
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

    @Override
    public boolean existsByNameAndIdNot(String name, Long id) {
        return repository.existsByNameAndIdNot(name, id);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
