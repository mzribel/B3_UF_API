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

    private Long litterId;

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
            Long litterId,
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
        this.litterId = litterId;
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
