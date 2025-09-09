package projet.uf.modules.cat.application;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.port.in.CatteryAuthorizationUseCase;
import projet.uf.modules.cat.application.command.LitterCommand;
import projet.uf.modules.cat.application.ports.in.CatAuthorizationUseCase;
import projet.uf.modules.cat.application.ports.in.CreateLitterUseCase;
import projet.uf.modules.cat.application.ports.in.LitterAuthorizationUseCase;
import projet.uf.modules.cat.application.ports.in.LitterUseCase;
import projet.uf.modules.cat.application.ports.out.LitterPersistencePort;
import projet.uf.modules.cat.domain.model.Cat;
import projet.uf.modules.cat.domain.model.Litter;

import java.util.List;

@AllArgsConstructor
public class LitterService implements LitterUseCase, CreateLitterUseCase {
    private final LitterPersistencePort litterPersistencePort;
    private final LitterAuthorizationUseCase litterAccessUseCase;
    private final CatteryAuthorizationUseCase catteryAccessUseCase;
    private final CatAuthorizationUseCase catAccessUseCase;

    @Override
    @Cacheable(value = "litters:all")
    public List<Litter> getAll(OperatorUser operator) {
        if (!operator.isAdmin()) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }
        return litterPersistencePort.getAll();
    }

    @Cacheable(value = "litters", key = "#litterId")
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
        // L'utilisateur n'a pas les droits pour accéder à la chatterie d'appartenance
        if (!catteryAccessUseCase.hasUserAccessToCattery(createdByCatteryId, operator)) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }

        // Récupère les parents
        Cat sire = null; Cat dam = null;

        if (command.getDamId() != null) {
            dam = catAccessUseCase.getCatOrThrow(command.getDamId(), operator);
        }
        if (command.getSireId() != null) {
            sire = catAccessUseCase.getCatOrThrow(command.getSireId(), operator);
        }

        // Vérifie la validité des parents (vivants, non-castrés, du bon sexe,
        // au moins six mois et plus vieux que la portée d'au moins six mois si date définie
        if ((sire != null && !sire.validateParentEligibility(false, command.getBirthDate()) ||
                (dam != null && !dam.validateParentEligibility(true, command.getBirthDate())
        ))) {
            throw new ApiException("L'un des deux parents n'est pas valide", HttpStatus.BAD_REQUEST);
        }

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
}
