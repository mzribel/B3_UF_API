package projet.uf.modules.cat.model;

import org.junit.jupiter.api.Test;
import projet.uf.modules.cat.domain.model.Litter;

import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

class LitterTest {

    @Test
    void shouldCreateLitterWithAllArgsConstructor() {
        // Given
        Long id = 1L;
        Long sireId = 2L;
        Long damId = 3L;
        Long originBreederId = 4L;
        LocalDate birthDate = LocalDate.of(2023, 5, 10);
        String loofIdentificationNumber = "LOOF123";
        LocalDate loofDeclarationDate = LocalDate.of(2023, 5, 12);
        int kittenCount = 5;
        String notes = "A beautiful litter.";
        Long createdByCatteryId = 6L;

        // When
        Litter litter = new Litter(id, sireId, damId, originBreederId, birthDate, loofIdentificationNumber, loofDeclarationDate, kittenCount, notes, createdByCatteryId);

        // Then
        assertThat(litter.getId()).isEqualTo(id);
        assertThat(litter.getSireId()).isEqualTo(sireId);
        assertThat(litter.getDamId()).isEqualTo(damId);
        assertThat(litter.getOriginBreederId()).isEqualTo(originBreederId);
        assertThat(litter.getBirthDate()).isEqualTo(birthDate);
        assertThat(litter.getLoofIdentificationNumber()).isEqualTo(loofIdentificationNumber);
        assertThat(litter.getLoofDeclarationDate()).isEqualTo(loofDeclarationDate);
        assertThat(litter.getKittenCount()).isEqualTo(kittenCount);
        assertThat(litter.getNotes()).isEqualTo(notes);
        assertThat(litter.getCreatedByCatteryId()).isEqualTo(createdByCatteryId);
    }

    @Test
    void shouldCreateLitterWithConstructorWithoutId() {
        // Given
        Long sireId = 2L;
        Long damId = 3L;
        Long originBreederId = 4L;
        LocalDate birthDate = LocalDate.of(2023, 5, 10);
        String loofIdentificationNumber = "LOOF123";
        LocalDate loofDeclarationDate = LocalDate.of(2023, 5, 12);
        int kittenCount = 5;
        String notes = "A beautiful litter.";
        Long createdByCatteryId = 6L;

        // When
        Litter litter = new Litter(sireId, damId, originBreederId, birthDate, loofIdentificationNumber, loofDeclarationDate, kittenCount, notes, createdByCatteryId);

        // Then
        assertThat(litter.getId()).isNull();
        assertThat(litter.getSireId()).isEqualTo(sireId);
        assertThat(litter.getDamId()).isEqualTo(damId);
        assertThat(litter.getOriginBreederId()).isEqualTo(originBreederId);
        assertThat(litter.getBirthDate()).isEqualTo(birthDate);
        assertThat(litter.getLoofIdentificationNumber()).isEqualTo(loofIdentificationNumber);
        assertThat(litter.getLoofDeclarationDate()).isEqualTo(loofDeclarationDate);
        assertThat(litter.getKittenCount()).isEqualTo(kittenCount);
        assertThat(litter.getNotes()).isEqualTo(notes);
        assertThat(litter.getCreatedByCatteryId()).isEqualTo(createdByCatteryId);
    }

    @Test
    void shouldCreateLitterWithBuilder() {
        // Given
        Long id = 1L;
        Long sireId = 2L;
        Long damId = 3L;
        Long originBreederId = 4L;
        LocalDate birthDate = LocalDate.of(2023, 5, 10);
        String loofIdentificationNumber = "LOOF123";
        LocalDate loofDeclarationDate = LocalDate.of(2023, 5, 12);
        int kittenCount = 5;
        String notes = "A beautiful litter.";
        Long createdByCatteryId = 6L;

        // When
        Litter litter = Litter.builder()
                .id(id)
                .sireId(sireId)
                .damId(damId)
                .originBreederId(originBreederId)
                .birthDate(birthDate)
                .loofIdentificationNumber(loofIdentificationNumber)
                .loofDeclarationDate(loofDeclarationDate)
                .kittenCount(kittenCount)
                .notes(notes)
                .createdByCatteryId(createdByCatteryId)
                .build();

        // Then
        assertThat(litter.getId()).isEqualTo(id);
        assertThat(litter.getSireId()).isEqualTo(sireId);
        assertThat(litter.getDamId()).isEqualTo(damId);
        assertThat(litter.getOriginBreederId()).isEqualTo(originBreederId);
        assertThat(litter.getBirthDate()).isEqualTo(birthDate);
        assertThat(litter.getLoofIdentificationNumber()).isEqualTo(loofIdentificationNumber);
        assertThat(litter.getLoofDeclarationDate()).isEqualTo(loofDeclarationDate);
        assertThat(litter.getKittenCount()).isEqualTo(kittenCount);
        assertThat(litter.getNotes()).isEqualTo(notes);
        assertThat(litter.getCreatedByCatteryId()).isEqualTo(createdByCatteryId);
    }
} 