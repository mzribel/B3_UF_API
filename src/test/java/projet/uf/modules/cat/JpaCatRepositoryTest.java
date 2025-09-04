package projet.uf.modules.cat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import projet.uf.modules.cat.adapter.out.persistence.cat.CatEntity;
import projet.uf.modules.cat.adapter.out.persistence.cat.JpaCatRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaCatRepositoryTest {

    @Container
    static MariaDBContainer<?> db = new MariaDBContainer<>("mariadb:11.4");

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", db::getJdbcUrl);
        r.add("spring.datasource.username", db::getUsername);
        r.add("spring.datasource.password", db::getPassword);
        r.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
        r.add("spring.flyway.enabled", () -> "true");
    }

    @Autowired
    JpaCatRepository repo;

    @Test
    void whenSavingNewCat_thenSuccess() {
        CatEntity cat = new CatEntity();
        cat.setName("Whiskers");

        CatEntity savedCat = repo.save(cat);

        assertThat(savedCat.getId()).isNotNull();
        assertThat(savedCat.getName()).isEqualTo("Whiskers");
    }

    @Test
    void whenFindingCatById_thenSuccess() {
        CatEntity cat = new CatEntity();
        cat.setName("Mittens");
        CatEntity savedCat = repo.save(cat);

        var foundCat = repo.findById(savedCat.getId());

        assertThat(foundCat).isPresent();
        assertThat(foundCat.get().getName()).isEqualTo("Mittens");
    }

    @Test
    void whenUpdatingCat_thenSuccess() {
        CatEntity cat = new CatEntity();
        cat.setName("Original");
        CatEntity savedCat = repo.save(cat);

        savedCat.setName("Updated");
        CatEntity updatedCat = repo.save(savedCat);

        assertThat(updatedCat.getName()).isEqualTo("Updated");
    }

    @Test
    void whenDeletingCat_thenSuccess() {
        CatEntity cat = new CatEntity();
        cat.setName("ToDelete");
        CatEntity savedCat = repo.save(cat);

        repo.deleteById(savedCat.getId());

        assertThat(repo.findById(savedCat.getId())).isEmpty();
    }

    @Test
    void whenFindingAllCats_thenSuccess() {
        CatEntity cat1 = new CatEntity();
        cat1.setName("Cat1");
        CatEntity cat2 = new CatEntity();
        cat2.setName("Cat2");

        repo.save(cat1);
        repo.save(cat2);

        var allCats = repo.findAll();

        assertThat(allCats).hasSize(2);
    }
}