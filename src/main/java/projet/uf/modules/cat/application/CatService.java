package projet.uf.modules.cat.application;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.port.in.CatteryAccessUseCase;
import projet.uf.modules.cat.application.ports.in.*;
import projet.uf.modules.cat.application.ports.out.CatPersistencePort;
import projet.uf.modules.cat.domain.model.Cat;

import java.util.List;

@AllArgsConstructor
public class CatService implements
        CatUseCase
{
    private final CatPersistencePort catPersistencePort;
    private final CatteryAccessUseCase catteryAccessUseCase;
    private final CatAccessUseCase catAccessUseCase;

    @Override
    public Cat createCat(CreateCatCommand command, Long createdByCatteryId, OperatorUser operator) {
        if (!catteryAccessUseCase.hasUserAccessToCattery(createdByCatteryId, operator)) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }

        Cat cat = CatCommandMapper.fromCreateCommand(command, createdByCatteryId);
        return catPersistencePort.save(cat);
    }

    @Override
    public Cat updateCatById(Long id, CreateCatCommand command, OperatorUser operator) {
        Cat cat = catAccessUseCase.getCatOrThrow(id, operator);
        Cat updatedCat = CatCommandMapper.fromCreateCommand(command, cat.getCreatedByCatteryId());
        updatedCat.setId(cat.getId());
        return catPersistencePort.save(updatedCat);
    }

    @Override
    public void deleteCatById(Long id, OperatorUser operator) {
        if (!catAccessUseCase.hasUserAccessToCat(id, operator)) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }
        catPersistencePort.deleteById(id);
    }

    @Override
    public Cat getById(Long id, OperatorUser operator) {
        return catAccessUseCase.getCatOrThrow(id, operator);
    }

    @Override
    public List<Cat> getByCatteryId(Long id, OperatorUser operator) {
        if (!catteryAccessUseCase.hasUserAccessToCattery(id, operator)) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }
        return catPersistencePort.getByCatteryId(id);
    }

    @Override
    public List<Cat> getAll(OperatorUser operator) {
        if (!operator.isAdmin()) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }
        return catPersistencePort.getAll();
    }
}
