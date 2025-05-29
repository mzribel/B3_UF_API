package projet.uf.modules.loof_characteristic.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class LoofCharacteristicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String details;

    public LoofCharacteristicEntity(String code, String name, String details) {
        this.code = code;
        this.name = name;
        this.details = details;
    }
}
