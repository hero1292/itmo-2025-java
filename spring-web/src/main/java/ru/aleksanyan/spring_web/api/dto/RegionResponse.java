package ru.aleksanyan.spring_web.api.dto;

public record RegionResponse(
        Long id,
        String code,
        String nameRu,
        String nameEn
) {}
