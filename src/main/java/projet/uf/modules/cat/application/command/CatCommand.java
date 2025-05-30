package projet.uf.modules.cat.application.command;

import jakarta.validation.constraints.*;
import projet.uf.modules.cat.domain.model.Cat;

import java.time.LocalDate;

public record CatCommand(
    @NotBlank(message = "Le nom du chat ne peut pas être vide")
    @NotBlank(message = "Le nom du chat ne peut pas être vide")
    @Size(max = 30, message = "Le nom du chat ne peut pas dépasser 30 caractères")
    String name,
    @Size(max = 30, message = "Le surnom du chat ne peut pas dépasser 30 caractères")
    String surname,
    boolean sex,
    @PastOrPresent(message = "Le chat doit être né à une date antérieure ou égale à aujourd'hui")
    LocalDate birthDate,

    String pedigreeNo,
    String identificationNo,

    Boolean isNeutered,
    LocalDate neuteredDate,

    Boolean isDeceased,
    LocalDate deceasedDate,

    Long originBreederId,
    Long currentBreederId,

    Boolean isInCattery,
    String notes,

    Long litterId,
    CatCoatCommand coat
){
    public static Cat toModel(CatCommand command, Long createdByCatteryId) {
        return new Cat(
                command.name(),
                command.surname(),
                command.sex(),
                command.birthDate(),
                command.litterId(),
                command.originBreederId(),
                command.currentBreederId(),
                null,
                null,
                null,
                command.pedigreeNo(),
                command.identificationNo(),
                Boolean.TRUE.equals(command.isNeutered()),
                command.neuteredDate(),
                Boolean.TRUE.equals(command.isDeceased()),
                command.deceasedDate(),
                createdByCatteryId,
                Boolean.TRUE.equals(command.isInCattery()),
                command.notes()
        );
    }
}
