package projet.uf.modules.loof_characteristics.application.ports.in;

public interface CreateLoofCharacteristicUseCase<T> {
    T create(CreateLoofCharacteristicCommand command) throws Exception;
}
