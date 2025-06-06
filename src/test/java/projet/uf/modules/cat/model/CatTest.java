package projet.uf.modules.cat.model;

import org.junit.jupiter.api.Test;
import projet.uf.modules.cat.domain.model.Cat;

import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

class CatTest {

    @Test
    void shouldCreateCatWithAllArgsConstructor() {
        // Given
        Long id = 1L;
        String name = "Misty";
        String surname = "The Cat";
        boolean sex = true; // true for female
        Long litterId = 2L;
        Long breedId = 3L;
        Long eyeColorId = 4L;
        Long polyTypeId = 5L;
        String pedigreeNo = "PED123";
        String identificationNo = "ID456";
        boolean neutered = true;
        LocalDate neuteredDate = LocalDate.of(2022, 1, 1);
        boolean deceased = false;
        LocalDate deceasedDate = null;
        Long createdByCatteryId = 6L;
        boolean inCattery = true;
        String notes = "A very fine cat.";

        // When
        Cat cat = new Cat(id, name, surname, sex, litterId, breedId, eyeColorId, polyTypeId, pedigreeNo, identificationNo, neutered, neuteredDate, deceased, deceasedDate, createdByCatteryId, inCattery, notes);

        // Then
        assertThat(cat.getId()).isEqualTo(id);
        assertThat(cat.getName()).isEqualTo(name);
        assertThat(cat.getSurname()).isEqualTo(surname);
        assertThat(cat.isSex()).isEqualTo(sex);
        assertThat(cat.getLitterId()).isEqualTo(litterId);
        assertThat(cat.getBreedId()).isEqualTo(breedId);
        assertThat(cat.getEyeColorId()).isEqualTo(eyeColorId);
        assertThat(cat.getPolyTypeId()).isEqualTo(polyTypeId);
        assertThat(cat.getPedigreeNo()).isEqualTo(pedigreeNo);
        assertThat(cat.getIdentificationNo()).isEqualTo(identificationNo);
        assertThat(cat.isNeutered()).isEqualTo(neutered);
        assertThat(cat.getNeuteredDate()).isEqualTo(neuteredDate);
        assertThat(cat.isDeceased()).isEqualTo(deceased);
        assertThat(cat.getDeceasedDate()).isEqualTo(deceasedDate);
        assertThat(cat.getCreatedByCatteryId()).isEqualTo(createdByCatteryId);
        assertThat(cat.isInCattery()).isEqualTo(inCattery);
        assertThat(cat.getNotes()).isEqualTo(notes);
    }

    @Test
    void shouldCreateCatWithConstructorWithoutId() {
        // Given
        String name = "Smoky";
        String surname = "The Other Cat";
        boolean sex = false; // false for male
        Long litterId = 2L;
        Long breedId = 3L;
        Long eyeColorId = 4L;
        Long polyTypeId = 5L;
        String pedigreeNo = "PED124";
        String identificationNo = "ID457";
        boolean neutered = false;
        LocalDate neuteredDate = null;
        boolean deceased = false;
        LocalDate deceasedDate = null;
        Long createdByCatteryId = 6L;
        boolean inCattery = true;
        String notes = "Another fine cat.";

        // When
        Cat cat = new Cat(name, surname, sex, litterId, breedId, eyeColorId, polyTypeId, pedigreeNo, identificationNo, neutered, neuteredDate, deceased, deceasedDate, createdByCatteryId, inCattery, notes);

        // Then
        assertThat(cat.getId()).isNull();
        assertThat(cat.getName()).isEqualTo(name);
        assertThat(cat.getSurname()).isEqualTo(surname);
        assertThat(cat.isSex()).isEqualTo(sex);
        assertThat(cat.getLitterId()).isEqualTo(litterId);
        assertThat(cat.getBreedId()).isEqualTo(breedId);
        assertThat(cat.getEyeColorId()).isEqualTo(eyeColorId);
        assertThat(cat.getPolyTypeId()).isEqualTo(polyTypeId);
        assertThat(cat.getPedigreeNo()).isEqualTo(pedigreeNo);
        assertThat(cat.getIdentificationNo()).isEqualTo(identificationNo);
        assertThat(cat.isNeutered()).isEqualTo(neutered);
        assertThat(cat.getNeuteredDate()).isEqualTo(neuteredDate);
        assertThat(cat.isDeceased()).isEqualTo(deceased);
        assertThat(cat.getDeceasedDate()).isEqualTo(deceasedDate);
        assertThat(cat.getCreatedByCatteryId()).isEqualTo(createdByCatteryId);
        assertThat(cat.isInCattery()).isEqualTo(inCattery);
        assertThat(cat.getNotes()).isEqualTo(notes);
    }
} 