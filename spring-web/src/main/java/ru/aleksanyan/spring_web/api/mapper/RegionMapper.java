package ru.aleksanyan.spring_web.api.mapper;

import org.springframework.stereotype.Component;
import ru.aleksanyan.spring_web.api.dto.RegionResponse;
import ru.aleksanyan.spring_web.domain.Region;

@Component
public class RegionMapper {
    public RegionResponse toResponse(Region r) {
        return new RegionResponse(r.getId(), r.getCode(), r.getNameRu(), r.getNameEn());
    }
}
