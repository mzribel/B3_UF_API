package projet.uf.modules.breeder.adapter.out.persistence.catteryuser;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class CatteryUserId implements Serializable {
    private Long catteryId;
    private Long userId;

    public CatteryUserId() {}

    public CatteryUserId(Long catteryId, Long userId) {
        this.catteryId = catteryId;
        this.userId = userId;
    }

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
