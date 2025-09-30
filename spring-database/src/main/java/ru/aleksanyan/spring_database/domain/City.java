package ru.aleksanyan.spring_database.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class City {
    private Long id;
    private String code;
    private String nameRu;
    private String nameEn;
    private long population;
    private Long regionId;
}
