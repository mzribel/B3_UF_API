package projet.uf.modules.loof_characteristic.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface JpaLoofCharacteristicRepository<T extends LoofCharacteristicEntity> extends JpaRepository<T, Long> {
    boolean existsByName(String name);
    Optional<T> findByName(String name);
    List<T> findByCode(String code);
}
