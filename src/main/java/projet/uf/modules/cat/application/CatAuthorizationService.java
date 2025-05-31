package projet.uf.modules.cat.application;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.port.in.CatteryAuthorizationUseCase;
import projet.uf.modules.cat.application.ports.in.CatAuthorizationUseCase;
import projet.uf.modules.cat.application.ports.out.CatPersistencePort;
import projet.uf.modules.cat.domain.model.Cat;

@AllArgsConstructor
public class CatAuthorizationService implements CatAuthorizationUseCase {
    private final CatPersistencePort catPersistencePort;
    private final CatteryAuthorizationUseCase catteryAccessUseCase;

    @Override
    public Cat getCatOrThrow(@NotNull Long catId, OperatorUser operator) {
        Cat cat = catPersistencePort.getById(catId)
                .orElseThrow(() -> new ApiException("Chat introuvable", HttpStatus.BAD_REQUEST));

        // Vérifie l’accès à la chatterie du chat
        catteryAccessUseCase.getCatteryOrThrow(cat.getCreatedByCatteryId(), operator);
        return cat;
    }

    @Override
    public boolean hasUserAccessToCat(@NotNull Long catId, OperatorUser operator) {
        Cat cat = catPersistencePort.getById(catId)
                .orElseThrow(() -> new ApiException("Chat introuvable", HttpStatus.BAD_REQUEST));

        return catteryAccessUseCase.hasUserAccessToCattery(cat.getCreatedByCatteryId(), operator);
    }
}
