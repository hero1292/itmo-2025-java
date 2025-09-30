package ru.aleksanyan.spring_web.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.aleksanyan.spring_web.domain.Region;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class RegionRepositoryTest {

    @Autowired RegionRepository repo;

    @Test
    void save_and_findByCode() {
        repo.save(Region.builder().code("ru-c").nameRu("Центр").nameEn("Center").build());
        assertThat(repo.existsByCode("ru-c")).isTrue();
        assertThat(repo.findByCode("ru-c")).isPresent();
    }
}