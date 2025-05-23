package projet.uf.modules.loof_characteristic.application.port.out;

import java.util.List;
import java.util.Optional;

public interface LoofCharacteristicPersistencePort<T> {
    T save(T t);
    Optional<T> getById(long id);
    List<T> getAll();
    List<T> getByCode(String code);
    Optional<T> getByName(String name);
    boolean existsByName(String name);
}
