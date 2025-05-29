package projet.uf.modules.cat.application.ports.in;

import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateCatCommand {
    @NotBlank(message = "Le nom du chat ne peut pas être vide")
    @NotBlank(message = "Le nom du chat ne peut pas être vide")
    @Size(max = 30, message = "Le nom du chat ne peut pas dépasser 30 caractères")
    private String name;
    @Size(max = 30, message = "Le surnom du chat ne peut pas dépasser 30 caractères")
    private String surname;
    private boolean sex;
    @PastOrPresent(message = "Le chat doit être né à une date antérieure ou égale à aujourd'hui")
    private LocalDate birthDate;

    private String pedigreeNo;
    private String identificationNo;

    private Boolean isNeutered;
    private LocalDate neuteredDate;

    private Boolean isDeceased;
    private LocalDate deceasedDate;

    private Long originBreederId;
    private Long currentBreederId;

    private Boolean isInCattery;
    private String notes;

    private Long litterId;
}
