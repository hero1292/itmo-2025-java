package ru.aleksanyan.spring_web.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CityDto(
        @NotBlank @Size(max = 32) String code,
        @NotBlank @Size(max = 255) String nameRu,
        @NotBlank @Size(max = 255) String nameEn,
        @PositiveOrZero long population,
        @Size(max = 32) String regionCode
) {}
