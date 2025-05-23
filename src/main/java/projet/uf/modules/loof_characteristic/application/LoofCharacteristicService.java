package projet.uf.modules.loof_characteristic.application;

import projet.uf.modules.loof_characteristic.application.mapper.LoofCharacteristicCommandMapper;
import projet.uf.modules.loof_characteristic.application.port.in.CreateLoofCharacteristicCommand;
import projet.uf.modules.loof_characteristic.application.port.in.CreateLoofCharacteristicUseCase;
import projet.uf.modules.loof_characteristic.application.port.in.GetLoofCharacteristicUseCase;
import projet.uf.modules.loof_characteristic.application.port.out.LoofCharacteristicPersistencePort;
import projet.uf.modules.loof_characteristic.domain.model.ALoofCharacteristic;

import java.util.List;
import java.util.Optional;

public class LoofCharacteristicService <T extends ALoofCharacteristic> implements
        CreateLoofCharacteristicUseCase<T>,
        GetLoofCharacteristicUseCase<T>
{
    public final LoofCharacteristicPersistencePort<T> persistencePort;
    private final LoofCharacteristicCommandMapper<T> mapper;

    public LoofCharacteristicService(LoofCharacteristicPersistencePort<T> persistencePort, LoofCharacteristicCommandMapper<T> mapper) {
        this.persistencePort = persistencePort;
        this.mapper = mapper;
    }

    @Override
    public T create(CreateLoofCharacteristicCommand command) throws Exception {
        if (persistencePort.existsByName(command.name())) {
            throw new Exception("A characteristic with this name already exists");
        }
        T entity = mapper.fromCreateCommand(command);
        return persistencePort.save(entity);
    }

    @Override
    public Optional<T> getById(Long id) {
        return persistencePort.getById(id);
    }

    @Override
    public List<T> getAll() {
        return persistencePort.getAll();
    }

    @Override
    public List<T> getByCode(String code) {
        return persistencePort.getByCode(code);
    }

    @Override
    public Optional<T> getByName(String name) {
        return persistencePort.getByName(name);
    }
}
