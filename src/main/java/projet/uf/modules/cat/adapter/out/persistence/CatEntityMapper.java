package projet.uf.modules.cat.adapter.out.persistence;

import projet.uf.modules.cat.domain.model.Cat;
import projet.uf.modules.user.adapters.out.persistence.JpaUserRepository;
import projet.uf.modules.user.adapters.out.persistence.UserPersistenceAdapter;

public class CatEntityMapper {

    public static Cat toModel(CatEntity entity) {
        return new Cat(
            entity.getId(),
            entity.getName(),
            entity.getSurname(),
            entity.isSex(),
            entity.getBirthDate(),
            entity.getLitterId(),
            entity.getOriginBreederId(),
            entity.getCurrentBreederId(),
            entity.getBreedId(),
            entity.getEyeColorId(),
            entity.getPolyTypeId(),
            entity.getPedigreeNo(),
            entity.getIdentificationNo(),
            entity.isNeutered(),
            entity.getNeuteredDate(),
            entity.isDeceased(),
            entity.getDeceasedDate(),
            entity.getCreatedByCatteryId(),
            entity.isInCattery(),
            entity.getNotes()
        );
    }

    public static CatEntity toEntity(Cat model) {
        return CatEntity.builder()
        .name(model.getName())
        .surname(model.getSurname())
        .sex(model.isSex())
        .birthDate(model.getBirthDate())
        .litterId(model.getLitterId())
        .originBreederId(model.getOriginBreederId())
        .currentBreederId(model.getCurrentBreederId())
        .breedId(model.getBreedId())
        .eyeColorId(model.getEyeColorId())
        .polyTypeId(model.getPolyTypeId())
        .pedigreeNo(model.getPedigreeNo())
        .identificationNo(model.getIdentificationNo())
        .isNeutered(model.isNeutered())
        .neuteredDate(model.getNeuteredDate())
        .isDeceased(model.isDeceased())
        .deceasedDate(model.getDeceasedDate())
        .createdByCatteryId(model.getCreatedByCatteryId())
        .isInCattery(model.isInCattery())
        .notes(model.getNotes())
        .build();
    }
}
