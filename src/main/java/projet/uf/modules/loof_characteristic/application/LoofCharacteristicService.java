package projet.uf.modules.loof_characteristic.application;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.loof_characteristic.application.mapper.LoofCharacteristicCommandMapper;
import projet.uf.modules.loof_characteristic.application.port.in.CreateLoofCharacteristicCommand;
import projet.uf.modules.loof_characteristic.application.port.in.LoofCharacteristicUseCase;
import projet.uf.modules.loof_characteristic.application.port.out.LoofCharacteristicPersistencePort;
import projet.uf.modules.loof_characteristic.domain.model.ALoofCharacteristic;
import projet.uf.modules.loof_characteristic.exception.LoofCharacteristicAlreadyExists;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LoofCharacteristicService<T extends ALoofCharacteristic> implements
        LoofCharacteristicUseCase<T>
{
    public final LoofCharacteristicPersistencePort<T> persistencePort;
    private final LoofCharacteristicCommandMapper<T> mapper;

    @Override
    @CacheEvict(value = "loof-characteristics:all", allEntries = true)
    public T create(CreateLoofCharacteristicCommand command) throws LoofCharacteristicAlreadyExists {
        if (persistencePort.existsByName(command.name())) {
            throw new LoofCharacteristicAlreadyExists("Une caractéristique avec ce nom existe déjà", HttpStatus.CONFLICT);
        }
        T entity = mapper.fromCreateCommand(command);
        return persistencePort.insert(entity);
    }

    @CacheEvict(value = "loof-characteristics", key = "#id")
    public T update(Long id, CreateLoofCharacteristicCommand command) throws LoofCharacteristicAlreadyExists {
        T entity = persistencePort.getById(id)
                .orElseThrow(() -> new ApiException("Aucun élément trouvé avec l'id " + id, HttpStatus.BAD_REQUEST));

        // Vérifie si un autre élément existe déjà avec le même nom/code/etc.
        boolean exists = persistencePort.existsByNameAndIdNot(command.name(), id);
        if (exists) {
            throw new LoofCharacteristicAlreadyExists("Une caractéristique avec ce nom existe déjà", HttpStatus.CONFLICT);
        }

        // Mise à jour de l'entité
        entity.setName(command.name());
        entity.setCode(command.code());
        entity.setDetails(command.details());

        return persistencePort.update(entity);
    }

    @Override
    @Cacheable(value = "loof-characteristics", key = "#id")
    public Optional<T> getById(Long id) {
        return persistencePort.getById(id);
    }

    @Override
    @Cacheable(value = "loof-characteristics:all")
    public List<T> getAll() {
        return persistencePort.getAll();
    }

    @Override
    @CacheEvict(value = "loof-characteristics", key = "#id")
    public void deleteById(Long id) {
        persistencePort.deleteById(id);
    }
}
