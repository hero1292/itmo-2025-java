package ru.aleksanyan.spring_web.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.aleksanyan.spring_web.domain.City;
import ru.aleksanyan.spring_web.domain.Region;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class CityRepositoryTest {

    @Autowired CityRepository cityRepo;
    @Autowired RegionRepository regionRepo;

    @Test
    void finders_work() {
        Region r = regionRepo.save(Region.builder().code("ru-c").nameRu("Центр").nameEn("Center").build());
        cityRepo.save(City.builder().code("msc").nameRu("Москва").nameEn("Moscow").population(10).region(r).build());
        cityRepo.save(City.builder().code("spb").nameRu("Санкт-Петербург").nameEn("Saint-Petersburg").population(5).region(r).build());

        assertThat(cityRepo.existsByCode("msc")).isTrue();

        List<City> all = cityRepo.findAllByOrderByNameRuAsc();
        assertThat(all).hasSize(2);

        List<City> byRegion = cityRepo.findByRegion_IdOrderByNameRuAsc(r.getId());
        assertThat(byRegion).extracting(City::getCode).containsExactly("msc","spb");
    }
}