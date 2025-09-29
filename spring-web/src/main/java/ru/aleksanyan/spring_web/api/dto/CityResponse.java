package ru.aleksanyan.spring_web.api.dto;

public record CityResponse(
        Long id,
        String code,
        String nameRu,
        String nameEn,
        long population,
        String regionCode
) {}
