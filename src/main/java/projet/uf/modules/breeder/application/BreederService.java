package projet.uf.modules.breeder.application;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.auth.application.model.OperatorUser;
import projet.uf.modules.breeder.application.command.UpdateCatteryBreederCommand;
import projet.uf.modules.breeder.application.port.in.BreederUseCase;
import projet.uf.modules.breeder.application.command.CreateContactBreederCommand;
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
    public Breeder createContactBreeder(CreateContactBreederCommand command, Long createdByCatteryId, OperatorUser operator) {
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
        Breeder breeder = CreateContactBreederCommand.toModel(command, createdByCatteryId);
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
            throw new ApiException("Ce contact éleveur ne fait pas partie de la chatterie", HttpStatus.BAD_REQUEST);
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
        Breeder updatedBreeder = CreateContactBreederCommand.toModel(command, catteryId);
        updatedBreeder.setId(breederId);
        return breederPersistencePort.save(updatedBreeder);
    }

    @Override
    public void deleteContactBreeder(Long breederId, Long catteryId, OperatorUser operator) {
        // Récupère le breeder
        Breeder breeder = breederPersistencePort.getById(breederId)
                .orElseThrow(() -> new ApiException("Breeder introuvable", HttpStatus.BAD_REQUEST));
        // Vérifie qu'il est bien associé au catteryId fourni
        if (!breeder.getCreatedByCatteryId().equals(catteryId)) {
            throw new ApiException("Breeder introuvable", HttpStatus.BAD_REQUEST);
        }

        // Récupère la chatterie
        Cattery cattery = catteryPersistencePort.getById(catteryId).orElseThrow(() -> new ApiException("Chatterie introuvable", HttpStatus.BAD_REQUEST));

        if (!operator.isAdmin() && !cattery.getCreatedByUserId().equals(operator.getId())) {
            throw new ApiException("Accès non autorisé", HttpStatus.UNAUTHORIZED);
        }

        if (catteryPersistencePort.isBreederLinkedToAnyCattery(breederId)) {
            throw new ApiException("Un éleveur dont les informations sont liées à une chatterie ne peut pas être supprimé", HttpStatus.CONFLICT);
        }

        breederPersistencePort.deleteById(breederId);
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
        Breeder updatedBreeder = UpdateCatteryBreederCommand.toModel(command, catteryId);

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
    public Breeder getById(Long id, OperatorUser operator) {
        if (!operator.isAdmin()) {
            throw new ApiException("Accès non autorisé", HttpStatus.UNAUTHORIZED);
        }
        return breederPersistencePort.getById(id).orElseThrow(()-> new ApiException("Eleveur introuvable", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Breeder> getAll(OperatorUser operator) {
        if (operator.isAdmin()) {
            throw new ApiException("Accès non autorisé", HttpStatus.UNAUTHORIZED);
        }
        return breederPersistencePort.getAll();
    }

    @Override
    public void deleteById(Long breederId, OperatorUser operator) {
        if (!operator.isAdmin()) {
            throw new ApiException("Accès non autorisé", HttpStatus.UNAUTHORIZED);
        }

        // Récupère le breeder
        breederPersistencePort.getById(breederId)
            .orElseThrow(() -> new ApiException("Breeder introuvable", HttpStatus.BAD_REQUEST));

        if (catteryPersistencePort.isBreederLinkedToAnyCattery(breederId)) {
            throw new ApiException("Un éleveur dont les informations sont liées à une chatterie ne peut pas être supprimé", HttpStatus.CONFLICT);
        }

        breederPersistencePort.deleteById(breederId);
    }

    @Override
    public Breeder getCatteryBreederByCatteryId(Long catteryId, OperatorUser operator) {
        // Récupère la chatterie source
        Cattery cattery = catteryPersistencePort.getById(catteryId)
            .orElseThrow(() -> new ApiException("Chatterie introuvable", HttpStatus.BAD_REQUEST));
        // Vérifie les droits utilisateur
        if (!operator.isAdmin() && !cattery.getCreatedByUserId().equals(operator.getId())) {
            throw new ApiException("Seul un administrateur ou l'administrateur de la chatterie accéder aux données", HttpStatus.UNAUTHORIZED);
        }

        return breederPersistencePort.getBreederLinkedToCatteryId(catteryId)
            .orElseThrow(()-> new ApiException("La chatterie n'a pas encore entré d'informations éleveur", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Breeder> getAllContactBreedersByCatteryId(Long catteryId, OperatorUser operator) {
        // Récupère la chatterie source
        Cattery cattery = catteryPersistencePort.getById(catteryId)
                .orElseThrow(() -> new ApiException("Chatterie introuvable", HttpStatus.BAD_REQUEST));
        // Vérifie les droits utilisateur
        if (!operator.isAdmin() && !cattery.getCreatedByUserId().equals(operator.getId())) {
            throw new ApiException("Seul un administrateur ou l'administrateur de la chatterie accéder aux données", HttpStatus.UNAUTHORIZED);
        }
        return breederPersistencePort.getContactsByCatteryId(catteryId);
    }
}
