package projet.uf.modules.cat.application.ports.in;

import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateCatCommand {
    @NotBlank
    private String name;
    private String surname;
    private boolean sex;
    private LocalDate birthDate;

    private Long breedId;
    private Long eyeColorId;
    private Long polyTypeId;

    private String pedigreeNo;
    private String identificationNo;

    private Boolean isNeutered;
    private LocalDate neuteredDate;

    private Boolean isDeceased;
    private LocalDate deceasedDate;

    private Long originBreederId;
    private Long currentBreederId;

    private Long createdByCatteryId;
    private Boolean isInCattery;
    private String notes;

    private Long litterId;
}
