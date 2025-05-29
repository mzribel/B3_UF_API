package projet.uf.modules.cat.application.ports.model;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateCatCommand(
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
){}
