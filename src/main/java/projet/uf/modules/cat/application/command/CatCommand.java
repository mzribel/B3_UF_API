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

    String pedigreeNo,
    String identificationNo,

    Boolean isNeutered,
    LocalDate neuteredDate,
    Boolean isDeceased,
    LocalDate deceasedDate,

    Boolean isInCattery,
    String notes,

    Long litterId,
    LitterCommand litter,

    CatCoatCommand coat
){
    public static Cat toModel(CatCommand command, Long createdByCatteryId) {
        return new Cat(
                command.name(),
                command.surname(),
                command.sex(),
                command.litterId(),
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
