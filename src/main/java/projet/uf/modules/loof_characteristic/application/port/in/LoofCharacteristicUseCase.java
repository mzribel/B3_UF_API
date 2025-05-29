package projet.uf.modules.loof_characteristic.application.port.in;

import projet.uf.modules.loof_characteristic.exception.LoofCharacteristicAlreadyExists;

import java.util.List;
import java.util.Optional;

public interface LoofCharacteristicUseCase<T> {
    Optional<T> getById(Long id);
    List<T> getAll();

    void deleteById(Long id);

    T create(CreateLoofCharacteristicCommand command) throws LoofCharacteristicAlreadyExists;
    T update(Long id, CreateLoofCharacteristicCommand command) throws LoofCharacteristicAlreadyExists;
}
