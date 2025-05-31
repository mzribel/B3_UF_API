package projet.uf.modules.cat.application;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.port.in.CatteryAuthorizationUseCase;
import projet.uf.modules.cat.application.command.LitterCommand;
import projet.uf.modules.cat.application.dto.CatDetailsDto;
import projet.uf.modules.cat.application.dto.CatPedigreeDto;
import projet.uf.modules.cat.application.ports.in.*;
import projet.uf.modules.cat.application.command.CatCommand;
import projet.uf.modules.cat.application.ports.out.CatPersistencePort;
import projet.uf.modules.cat.domain.model.Cat;
import projet.uf.modules.cat.domain.model.Litter;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class CatService implements
        CatUseCase
{
    private final CatPersistencePort catPersistencePort;
    private final CatteryAuthorizationUseCase catteryAccessUseCase;
    private final CatAuthorizationUseCase catAccessUseCase;
    private final CatCoatUseCase catCoatUseCase;
    private final CatDtoAssembler dtoAssembler;
    private final LitterAuthorizationUseCase litterAuthorizationUseCase;
    private final CreateLitterUseCase createLitterUseCase;

    @Override
    @Transactional
    public CatDetailsDto createCat(CatCommand command, Long createdByCatteryId, OperatorUser operator) {
        if (!catteryAccessUseCase.hasUserAccessToCattery(createdByCatteryId, operator)) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }

        Cat cat = CatCommand.toModel(command, createdByCatteryId);

        // Crée la portée
        if (command.litter() != null) {
            if (command.litterId() != null) {
                throw new ApiException("Ne pas renseigner à la fois une portée existante et une nouvelle portée", HttpStatus.BAD_REQUEST);
            }

            cat.setLitterId(createLitterUseCase.createLitter(command.litter(), createdByCatteryId, operator).getId());
        }

        // Récupère la portée
        else if (command.litterId() != null) {
            Litter litter = litterAuthorizationUseCase.getLitterOrThrow(command.litterId(), operator);
            if (!litter.getCreatedByCatteryId().equals(createdByCatteryId)) {
                throw new ApiException("La portée doit faire partie de la même chatterie que le chat", HttpStatus.BAD_REQUEST);
            }
        }

        Cat saved = catPersistencePort.save(cat);

        if (command.coat() != null) {
            catCoatUseCase.createOrUpdateCatCoat(saved.getId(), command.coat());
        }

        return dtoAssembler.toDetailsDto(saved);
    }

    @Override
    @Transactional
    public CatDetailsDto updateCatById(Long id, CatCommand command, OperatorUser operator) {
        Cat cat = catAccessUseCase.getCatOrThrow(id, operator);
        Cat updatedCat = CatCommand.toModel(command, cat.getCreatedByCatteryId());
        updatedCat.setId(cat.getId());
        return dtoAssembler.toDetailsDto(catPersistencePort.save(updatedCat));
    }

    @Override
    @Transactional
    public void deleteCatById(Long id, OperatorUser operator) {
        if (!catAccessUseCase.hasUserAccessToCat(id, operator)) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }
        catPersistencePort.deleteById(id);
    }

    @Override
    public CatPedigreeDto getPedigreeById(Long id, OperatorUser operator) {
        Cat cat = catAccessUseCase.getCatOrThrow(id, operator);

        return dtoAssembler.toPedigreeDto(cat, 3);
    }

    @Override
    public List<CatDetailsDto> getCatsByLitterId(Long litterId, OperatorUser operator) {
        Litter litter = litterAuthorizationUseCase.getLitterOrThrow(litterId, operator);

        return catPersistencePort.getByLitterIdAndCatteryId(litterId, litter.getCreatedByCatteryId())
                .stream()
                .map(dtoAssembler::toDetailsDto)
                .toList();
    }

    @Override
    public Litter updateCatLitter(Long catId, LitterCommand command, OperatorUser operator) {
        return null;
    }

    @Override
    public void addKittenToLitter(Long catId, Long litterId, OperatorUser operator) {
        Litter litter = litterAuthorizationUseCase.getLitterOrThrow(litterId, operator);
        Cat cat = catAccessUseCase.getCatOrThrow(catId, operator);

        if (!cat.getCreatedByCatteryId().equals(litter.getCreatedByCatteryId())) {
            throw new ApiException("Le chat doit être relié à une portée de sa chatterie", HttpStatus.BAD_REQUEST);
        }

        cat.setLitterId(litterId);
        catPersistencePort.save(cat);
    }

    @Override
    public void removeKittenFromLitter(Long catId, Long litterId, OperatorUser operator) {
        litterAuthorizationUseCase.getLitterOrThrow(litterId, operator);
        Cat cat = catAccessUseCase.getCatOrThrow(catId, operator);

        if (!Objects.equals(cat.getLitterId(), litterId)) {
            throw new ApiException("Ce chat ne fait pas partie de cette portée", HttpStatus.BAD_REQUEST);
        }
        cat.setLitterId(null);
        catPersistencePort.save(cat);
    }

    @Override
    public CatDetailsDto getById(Long id, OperatorUser operator) {
        return dtoAssembler.toDetailsDto(catAccessUseCase.getCatOrThrow(id, operator));
    }

    @Override
    public List<CatDetailsDto> getByCatteryId(Long id, OperatorUser operator) {
        if (!catteryAccessUseCase.hasUserAccessToCattery(id, operator)) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }

        List<Cat> cats = catPersistencePort.getByCatteryId(id);

        return cats.stream()
                .map(dtoAssembler::toDetailsDto)
                .toList();
    }

    @Override
    public List<CatDetailsDto> getAll(OperatorUser operator) {
        if (!operator.isAdmin()) {
            throw new ApiException("Accès interdit", HttpStatus.FORBIDDEN);
        }

        return catPersistencePort.getAll()
                .stream()
                .map(dtoAssembler::toDetailsDto)
                .toList();
    }
}
