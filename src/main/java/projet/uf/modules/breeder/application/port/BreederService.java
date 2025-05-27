package projet.uf.modules.breeder.application.port;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.model.BreederCommandMapper;
import projet.uf.modules.breeder.application.model.UpdateCatteryBreederCommand;
import projet.uf.modules.breeder.application.port.in.BreederUseCase;
import projet.uf.modules.breeder.application.model.CreateContactBreederCommand;
import projet.uf.modules.breeder.application.port.out.BreederPersistencePort;
import projet.uf.modules.breeder.application.port.out.CatteryPersistencePort;
import projet.uf.modules.breeder.domain.model.Breeder;
import projet.uf.modules.breeder.domain.model.Cattery;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public class BreederService implements BreederUseCase {
    private final BreederPersistencePort breederPersistencePort;
    private final CatteryPersistencePort catteryPersistencePort;

    @Override
    public Breeder createContact(CreateContactBreederCommand command, Long createdByCatteryId, OperatorUser operator) {
        // Récupère la chatterie source
        Cattery cattery = catteryPersistencePort.getById(createdByCatteryId)
                .orElseThrow(() -> new ApiException("Chatterie introuvable", HttpStatus.BAD_REQUEST));

        // Vérifie les droits utilisateur
        if (!operator.isAdmin() && !cattery.getCreatedByUserId().equals(operator.getId())) {
            throw new ApiException("Seul un administrateur ou l'administrateur de la chatterie peuvent créer un éleveur contact", HttpStatus.UNAUTHORIZED);
        }

        // Vérifie l'existence d'un éleveur ayant déjà cet affixe
        if (breederPersistencePort.existsByAffixAndCatteryId(command.getAffix(), createdByCatteryId)) {
            throw new ApiException("La chatterie possède déjà un éleveur avec cet affixe", HttpStatus.CONFLICT);
        }

        // Créé l'éleveur contact
        Breeder breeder = BreederCommandMapper.fromCreateContactCommand(command, createdByCatteryId);
        return breederPersistencePort.save(breeder);
    }

    @Override
    public Breeder createEmptyCatteryBreeder(String name, Long createdByCatteryId) {
        Breeder breeder = new Breeder(name, createdByCatteryId);
        return breederPersistencePort.save(breeder);
    }

    public Breeder updateContactBreeder(Long breederId, Long catteryId, CreateContactBreederCommand command, OperatorUser operator) {
        // Récupère la chatterie source
        Cattery cattery = catteryPersistencePort.getById(catteryId)
                .orElseThrow(() -> new ApiException("Chatterie introuvable", HttpStatus.BAD_REQUEST));
        // Récupère l'éleveur
        Breeder breeder = breederPersistencePort.getById(breederId)
                .orElseThrow(() -> new ApiException("Contact éleveur introuvable", HttpStatus.BAD_REQUEST));

        // Vérifie la cohérence entre le catteryId et le breeder.createdByCatteryId
        if (!Objects.equals(cattery.getId(), breeder.getCreatedByCatteryId())) {
            throw new ApiException("Ce contact éleveur ne fait pas partie de la chatterie");
        }

        // Vérifie les droits utilisateur
        if (!operator.isAdmin() && !cattery.getCreatedByUserId().equals(operator.getId())) {
            throw new ApiException("Seul un administrateur ou l'administrateur de la chatterie peuvent modifier un éleveur contact", HttpStatus.UNAUTHORIZED);
        }

        // Vérifie l'existence d'un éleveur ayant déjà cet affixe
        if (breederPersistencePort.existsByAffixAndCatteryIdExceptId(command.getAffix(), catteryId, breederId)) {
            throw new ApiException("La chatterie possède déjà un éleveur avec cet affixe", HttpStatus.CONFLICT);
        }

        // Modifie l'éleveur contact
        Breeder updatedBreeder = BreederCommandMapper.fromCreateContactCommand(command, catteryId);
        updatedBreeder.setId(breederId);
        return breederPersistencePort.save(updatedBreeder);
    }

    @Transactional
    public Breeder updateCatteryBreeder(Long catteryId, UpdateCatteryBreederCommand command, OperatorUser operator) {
        // Récupère la chatterie source
        Cattery cattery = catteryPersistencePort.getById(catteryId)
                .orElseThrow(() -> new ApiException("Chatterie introuvable", HttpStatus.BAD_REQUEST));

        // Vérifie les droits utilisateur
        if (!operator.isAdmin() && !cattery.getCreatedByUserId().equals(operator.getId())) {
            throw new ApiException("Seul un administrateur ou l'administrateur de la chatterie peut modifier l'éleveur", HttpStatus.UNAUTHORIZED);
        }

        // Récupère l'éleveur actuel
        Long breederId = cattery.getLinkedToBreederId();
        Optional<Breeder> existingBreeder = (breederId != null)
                ? breederPersistencePort.getById(breederId)
                : Optional.empty();
        // Mapper le breeder depuis la commande
        Breeder updatedBreeder = BreederCommandMapper.fromUpdateBreederCommand(command, catteryId);

        if (existingBreeder.isEmpty()) {
            if (breederPersistencePort.existsByAffixAndCatteryId(command.getAffix(), catteryId)) {
                throw new ApiException("La chatterie possède déjà un éleveur avec cet affixe", HttpStatus.CONFLICT);
            }
            // Cas création
            Breeder savedBreeder = breederPersistencePort.save(updatedBreeder);
            cattery.setLinkedToBreederId(savedBreeder.getId());
            catteryPersistencePort.update(cattery);
            return savedBreeder;
        } else {
            // Vérifie l'existence d'un éleveur ayant déjà cet affixe
            if (breederPersistencePort.existsByAffixAndCatteryIdExceptId(command.getAffix(), catteryId, existingBreeder.get().getId())) {
                throw new ApiException("La chatterie possède déjà un éleveur avec cet affixe", HttpStatus.CONFLICT);
            }
            // Cas mise à jour
            updatedBreeder.setId(existingBreeder.get().getId());
            return breederPersistencePort.save(updatedBreeder);
        }
    }

    @Override
    public Optional<Breeder> getById(Long id) {
        return breederPersistencePort.getById(id);
    }

    @Override
    public List<Breeder> getAll() {
        return breederPersistencePort.getAll();
    }

    @Override
    public List<Breeder> getByCatteryId(Long id) {
        return breederPersistencePort.getByCatteryId(id);
    }

    @Override
    public Breeder update(Long id, CreateContactBreederCommand command) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        breederPersistencePort.deleteById(id);
    }
}
