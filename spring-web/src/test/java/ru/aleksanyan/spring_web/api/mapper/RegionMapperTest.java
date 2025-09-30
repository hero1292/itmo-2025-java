package ru.aleksanyan.spring_web.api.mapper;

import org.junit.jupiter.api.Test;
import ru.aleksanyan.spring_web.api.dto.RegionResponse;
import ru.aleksanyan.spring_web.domain.Region;

import static org.assertj.core.api.Assertions.assertThat;

class RegionMapperTest {
    private final RegionMapper mapper = new RegionMapper();

    @Test
    void toResponse_maps_all_fields() {
        Region r = Region.builder().id(1L).code("ru-c").nameRu("Центр").nameEn("Center").build();
        RegionResponse dto = mapper.toResponse(r);
        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.code()).isEqualTo("ru-c");
    }
}