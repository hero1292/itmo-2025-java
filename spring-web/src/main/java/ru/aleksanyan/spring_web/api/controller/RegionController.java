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
import ru.aleksanyan.spring_web.api.dto.RegionDto;
import ru.aleksanyan.spring_web.api.dto.RegionResponse;
import ru.aleksanyan.spring_web.api.error.ResourceNotFoundException;
import ru.aleksanyan.spring_web.api.mapper.RegionMapper;
import ru.aleksanyan.spring_web.domain.Region;
import ru.aleksanyan.spring_web.service.RegionService;

import java.util.List;

@Tag(name = "Regions", description = "Операции со справочником регионов")
@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
@Validated
public class RegionController {
    private final RegionService regionService;
    private final RegionMapper mapper;

    @PostMapping
    public RegionResponse create(@RequestBody @Valid RegionDto dto) {
        Region r = regionService.create(dto.code(), dto.nameRu(), dto.nameEn());

        return mapper.toResponse(r);
    }

    @GetMapping
    public Page<RegionResponse> list(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "20") int size,
                                     @RequestParam(defaultValue = "nameRu") String sort) {

        List<RegionResponse> items = regionService.list().stream()
                .sorted((a, b) -> switch (sort) {
                    case "nameEn" -> a.getNameEn().compareToIgnoreCase(b.getNameEn());
                    case "code" -> a.getCode().compareToIgnoreCase(b.getCode());
                    default -> a.getNameRu().compareToIgnoreCase(b.getNameRu());
                })
                .map(mapper::toResponse)
                .toList();

        int from = Math.min(page * size, items.size());
        int to = Math.min(from + size, items.size());

        return new PageImpl<>(items.subList(from, to), PageRequest.of(page, size, Sort.by(sort)), items.size());
    }

    @GetMapping("/{code}")
    public RegionResponse get(@PathVariable String code) {
        Region r = regionService.byCode(code)
                .orElseThrow(() -> new java.util.NoSuchElementException("Регион не найден: " + code));

        return mapper.toResponse(r);
    }

    @PutMapping("/{code}")
    public RegionResponse update(@PathVariable String code, @RequestBody @Valid RegionDto dto) {
        Region r = regionService.update(code, dto.nameRu(), dto.nameEn());

        return mapper.toResponse(r);
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String code) {
        boolean ok = regionService.delete(code);

        if (!ok) {
            throw new ResourceNotFoundException("Регион не найден: " + code);
        }
    }
}
