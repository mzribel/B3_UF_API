package projet.uf.modules.cat.application;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.port.in.CatteryAuthorizationUseCase;
import projet.uf.modules.cat.application.command.LitterCommand;
import projet.uf.modules.cat.application.ports.in.CatAuthorizationUseCase;
import projet.uf.modules.cat.application.ports.in.LitterAuthorizationUseCase;
import projet.uf.modules.cat.application.ports.in.LitterUseCase;
import projet.uf.modules.cat.application.ports.out.LitterPersistencePort;
import projet.uf.modules.cat.domain.model.Cat;
import projet.uf.modules.cat.domain.model.Litter;

import java.util.List;

@AllArgsConstructor
public class LitterService implements LitterUseCase {
    private final LitterPersistencePort litterPersistencePort;
    private final LitterAuthorizationUseCase litterAccessUseCase;
    private final CatteryAuthorizationUseCase catteryAccessUseCase;
    private final CatAuthorizationUseCase catAccessUseCase;

    @Override
    public List<Litter> getAll(OperatorUser operator) {
        if (!operator.isAdmin()) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }
        return litterPersistencePort.getAll();
    }

    @Override
    public Litter getById(Long litterId, OperatorUser operator) {
        return litterAccessUseCase.getLitterOrThrow(litterId, operator);
    }

    @Override
    public List<Litter> getByCatteryId(Long litterId, OperatorUser operator) {
        if (!litterAccessUseCase.hasUserAccessToLitter(litterId, operator)) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }
        return litterPersistencePort.getByCatteryId(litterId);
    }

    @Override
    public Litter createLitter(LitterCommand command, Long createdByCatteryId, OperatorUser operator) {
        if (!catteryAccessUseCase.hasUserAccessToCattery(createdByCatteryId, operator)) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }
        System.out.println(createdByCatteryId);
        Litter litter = command.toModel(createdByCatteryId);
        return litterPersistencePort.save(litter);
    }

    @Override
    public Litter updateLitterById(Long litterId, LitterCommand command, OperatorUser operator) {
        Litter litter = litterAccessUseCase.getLitterOrThrow(litterId, operator);
        Litter updatedLitter = command.toModel(litter.getCreatedByCatteryId());
        updatedLitter.setId(litterId);
        return litterPersistencePort.save(updatedLitter);
    }

    @Override
    public void deleteLitterById(Long litterId, OperatorUser operator) {
        if (!litterAccessUseCase.hasUserAccessToLitter(litterId, operator)) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }
        litterPersistencePort.deleteById(litterId);
    }

    @Override
    public Litter getCatLitter(Long catId, OperatorUser operator) {
        Cat cat = catAccessUseCase.getCatOrThrow(catId, operator);
        if (cat.getLitterId() == null) {
            throw new ApiException("Aucune portée associée à ce chat", HttpStatus.NOT_FOUND);
        }
        return litterPersistencePort.getById(cat.getLitterId()).orElse(null);
    }

    @Override
    public Litter updateCatLitter(Long catId, LitterCommand command, OperatorUser operator) {
        return null;
    }
}
