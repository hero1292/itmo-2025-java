package ru.aleksanyan.spring_web.api.mapper;

import org.junit.jupiter.api.Test;
import ru.aleksanyan.spring_web.api.dto.CityResponse;
import ru.aleksanyan.spring_web.domain.City;
import ru.aleksanyan.spring_web.domain.Region;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class CityMapperTest {
    private final CityMapper mapper = new CityMapper();

    @Test
    void toResponse_maps_all_fields() {
        Region r = Region.builder().id(2L).code("ru-c").build();
        City c = City.builder().id(1L).code("msc").nameRu("Москва").nameEn("Moscow").population(10).region(r).build();

        CityResponse dto = mapper.toResponse(c);

        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.regionCode()).isEqualTo("ru-c");
    }
}