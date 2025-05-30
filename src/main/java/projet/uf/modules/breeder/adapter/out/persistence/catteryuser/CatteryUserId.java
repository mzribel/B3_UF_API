package projet.uf.modules.breeder.adapter.out.persistence.catteryuser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CatteryUserId implements Serializable {
    private Long catteryId;
    private Long userId;

    // Obligatoire pour clé composite
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CatteryUserId that)) return false;
        return Objects.equals(catteryId, that.catteryId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(catteryId, userId);
    }
}
