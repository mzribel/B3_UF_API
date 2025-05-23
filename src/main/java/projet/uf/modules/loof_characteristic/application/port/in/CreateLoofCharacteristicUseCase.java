package projet.uf.modules.loof_characteristic.application.port.in;

public interface CreateLoofCharacteristicUseCase<T> {
    T create(CreateLoofCharacteristicCommand command) throws Exception;
}
