package projet.uf.modules.loof_characteristic.application.port.in;

import java.util.List;
import java.util.Optional;

public interface GetLoofCharacteristicUseCase<T> {
    Optional<T> getById(Long id);
    List<T> getAll();
    List<T> getByCode(String code);
    Optional<T> getByName(String name);
}
