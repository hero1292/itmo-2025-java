package ru.aleksanyan.spring_web.api.mapper;

import org.springframework.stereotype.Component;
import ru.aleksanyan.spring_web.api.dto.CityResponse;
import ru.aleksanyan.spring_web.domain.City;

@Component
public class CityMapper {
    public CityResponse toResponse(City c) {
        return new CityResponse(
                c.getId(),
                c.getCode(),
                c.getNameRu(),
                c.getNameEn(),
                c.getPopulation(),
                c.getRegion() != null ? c.getRegion().getCode() : null
        );
    }
}
