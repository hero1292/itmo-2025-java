package ru.aleksanyan.spring_web.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.aleksanyan.spring_web.api.dto.CityDto;
import ru.aleksanyan.spring_web.api.dto.CityResponse;
import ru.aleksanyan.spring_web.api.error.ResourceNotFoundException;
import ru.aleksanyan.spring_web.api.mapper.CityMapper;
import ru.aleksanyan.spring_web.domain.City;
import ru.aleksanyan.spring_web.service.CityService;

import java.util.List;

@Tag(name = "Cities", description = "Операции со справочником городов")
@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
@Validated
public class CityController {
    private final CityService cityService;
    private final CityMapper mapper;

    @PostMapping
    public CityResponse create(@RequestBody @Valid CityDto dto) {
        City city = cityService.create(
                dto.code(), dto.nameRu(), dto.nameEn(), dto.population(), dto.regionCode()
        );

        return mapper.toResponse(city);
    }

    @GetMapping
    public Page<CityResponse> list(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "20") int size,
                                   @RequestParam(defaultValue = "nameRu") String sort) {
        List<CityResponse> items = cityService.listAll().stream().map(mapper::toResponse).toList();
        int from = Math.min(page * size, items.size());
        int to = Math.min(from + size, items.size());
        List<CityResponse> content = items.subList(from, to);

        return new PageImpl<>(content, PageRequest.of(page, size, Sort.by(sort)), items.size());
    }

    @GetMapping(params = "region")
    public List<CityResponse> listByRegion(@RequestParam("region") String regionCode) {
        return cityService.listByRegion(regionCode).stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{code}")
    public CityResponse get(@PathVariable String code) {
        City c = cityService.findByCodeOrThrow(code);

        return mapper.toResponse(c);
    }

    @PutMapping("/{code}")
    public CityResponse update(@PathVariable String code, @RequestBody @Valid CityDto dto) {
        City c = cityService.update(code, dto.nameRu(), dto.nameEn(), dto.population());

        return mapper.toResponse(c);
    }

    @PatchMapping("/{code}/region")
    public String linkRegion(@PathVariable String code,
                             @RequestParam(required = false) String regionCode) {

        return cityService.relinkToRegion(code, regionCode);
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String code) {
        boolean ok = cityService.deleteByCode(code);

        if (!ok) {
            throw new ResourceNotFoundException("Город не найден: " + code);
        }
    }
}
