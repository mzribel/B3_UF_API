package projet.uf.modules.cat.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class Cat {
    private Long id;
    private String name;
    private String surname;
    private boolean sex; // enum recommandé : Sex

    private LocalDate birthDate;

    private Long litterId;
    private Long originBreederId;
    private Long currentBreederId;

    private Long breedId;
    private Long eyeColorId;
    private Long polyTypeId;

    private String pedigreeNo;
    private String identificationNo;

    private boolean neutered;
    private LocalDate neuteredDate;

    private boolean deceased;
    private LocalDate deceasedDate;

    private Long createdByCatteryId;
    private boolean inCattery;

    private String notes;

    // Constructeur sans id
    public Cat(
            String name,
            String surname,
            boolean sex,
            LocalDate birthDate,
            Long litterId,
            Long originBreederId,
            Long currentBreederId,
            Long breedId,
            Long eyeColorId,
            Long polyTypeId,
            String pedigreeNo,
            String identificationNo,
            boolean neutered,
            LocalDate neuteredDate,
            boolean deceased,
            LocalDate deceasedDate,
            Long createdByCatteryId,
            boolean inCattery,
            String notes
    ) {
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.birthDate = birthDate;
        this.litterId = litterId;
        this.originBreederId = originBreederId;
        this.currentBreederId = currentBreederId;
        this.breedId = breedId;
        this.eyeColorId = eyeColorId;
        this.polyTypeId = polyTypeId;
        this.pedigreeNo = pedigreeNo;
        this.identificationNo = identificationNo;
        this.neutered = neutered;
        this.neuteredDate = neuteredDate;
        this.deceased = deceased;
        this.deceasedDate = deceasedDate;
        this.createdByCatteryId = createdByCatteryId;
        this.inCattery = inCattery;
        this.notes = notes;
    }
}
