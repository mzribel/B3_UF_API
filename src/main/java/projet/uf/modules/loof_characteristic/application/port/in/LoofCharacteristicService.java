package projet.uf.modules.loof_characteristic.application.port.in;

import java.util.List;
import java.util.Optional;

public interface LoofCharacteristicService<T> {
    Optional<T> getById(Long id);
    List<T> getAll();
    List<T> getByCode(String code);
    Optional<T> getByName(String name);

    T create(CreateLoofCharacteristicCommand command) throws Exception;
}
