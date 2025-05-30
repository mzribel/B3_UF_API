package projet.uf.modules.cat.application;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.port.in.CatteryAccessUseCase;
import projet.uf.modules.cat.application.ports.in.LitterAccessUseCase;
import projet.uf.modules.cat.application.ports.out.LitterPersistencePort;
import projet.uf.modules.cat.domain.model.Litter;

@AllArgsConstructor
public class LitterAccessService implements LitterAccessUseCase {
    private final LitterPersistencePort litterPersistencePort;
    private final CatteryAccessUseCase catteryAccessUseCase;

    @Override
    public Litter getLitterOrThrow(@NotNull Long litterId, OperatorUser operator) {
        Litter litter = litterPersistencePort.getById(litterId)
                .orElseThrow(() -> new ApiException("Portée introuvable", HttpStatus.BAD_REQUEST));

        // Vérifie l’accès à la chatterie du chat
        catteryAccessUseCase.getCatteryOrThrow(litter.getCreatedByCatteryId(), operator);
        return litter;
    }

    @Override
    public boolean hasUserAccessToLitter(@NotNull Long litterId, OperatorUser operator) {
        Litter litter = litterPersistencePort.getById(litterId)
                .orElseThrow(() -> new ApiException("Chat introuvable", HttpStatus.BAD_REQUEST));

        return catteryAccessUseCase.hasUserAccessToCattery(litter.getCreatedByCatteryId(), operator);
    }
}
