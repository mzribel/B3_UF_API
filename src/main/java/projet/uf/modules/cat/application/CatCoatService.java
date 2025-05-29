package projet.uf.modules.cat.application;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import projet.uf.exceptions.ApiException;
import projet.uf.modules.cat.application.ports.dto.CatCoatDto;
import projet.uf.modules.cat.application.ports.model.CatCoatCommand;
import projet.uf.modules.cat.application.ports.model.CatCoatCommandMapper;
import projet.uf.modules.cat.application.ports.in.CatCoatUseCase;
import projet.uf.modules.cat.application.ports.out.CatCoatPersistencePort;
import projet.uf.modules.cat.application.ports.out.CatPersistencePort;
import projet.uf.modules.cat.domain.model.CatCoat;
import projet.uf.modules.loof_characteristic.application.port.in.AllLoofCharacteristicsUseCase;
import projet.uf.modules.loof_characteristic.domain.model.*;

import java.util.Optional;
import java.util.function.Function;

@AllArgsConstructor
public class CatCoatService implements CatCoatUseCase {
    private final CatCoatPersistencePort catCoatPersistencePort;
    private final CatPersistencePort catPersistencePort;
    private final AllLoofCharacteristicsUseCase loofCharacteristicsUseCase;

    @Override
    public CatCoat getCatCoatByCatId(Long id) {
        return catCoatPersistencePort.getByCatId(id).orElse(null);
    }

    @Override
    public CatCoat createOrUpdateCatCoat(Long catId, CatCoatCommand command) {
        if (catPersistencePort.getById(catId).isEmpty()) {
            throw new ApiException("Chat introuvable", HttpStatus.BAD_REQUEST);
        }

        CatCoat catCoat = CatCoatCommandMapper.fromCommand(command, catId);
        return catCoatPersistencePort.save(catCoat);
    }

    @Override
    public CatCoatDto toDto(@NotNull CatCoat catCoat, Breed breed) {
        CoatColor color = loofCharacteristicsUseCase.getCoatColorById(catCoat.getCoatColorId())
            .orElseThrow(() -> new ApiException("Couleur de robe inconnue", HttpStatus.INTERNAL_SERVER_ERROR));

        CoatPattern pattern = getLoofCharacteristic(catCoat.getCoatPatternId(), loofCharacteristicsUseCase::getCoatPatternById);
        CoatEffect effect = getLoofCharacteristic(catCoat.getCoatEffectId(), loofCharacteristicsUseCase::getCoatEffectById);
        CoatWhiteMarking whiteMarking = getLoofCharacteristic(catCoat.getCoatWhiteMarkingId(), loofCharacteristicsUseCase::getCoatWhiteMarkingById);

            return CatCoatDto.toDto(
//                    breed.getCode(),
//                    breed.getName(),
                    "MCO",
                    "Maine Coon",
                    color,
                    pattern,
                    effect,
                    whiteMarking
            );
    }
    private <T> T getLoofCharacteristic(Long id, Function<Long, Optional<T>> fetcher) {
        return Optional.ofNullable(id)
                .flatMap(fetcher)
                .orElse(null);
    }
}
