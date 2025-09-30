package ru.aleksanyan.spring_web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aleksanyan.spring_web.domain.City;
import ru.aleksanyan.spring_web.domain.Region;
import ru.aleksanyan.spring_web.repo.CityRepository;
import ru.aleksanyan.spring_web.repo.RegionRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepo;
    private final RegionRepository regionRepo;

    @Transactional
    public City create(String code, String nameRu, String nameEn, long population, String regionCodeOrNull) {
        validate(code, nameRu, nameEn, population);
        if (cityRepo.existsByCode(code)) throw new DuplicateKeyException("Город уже существует: " + code);

        Region region = null;

        if (regionCodeOrNull != null && !regionCodeOrNull.isBlank()) {
            region = regionRepo.findByCode(regionCodeOrNull)
                    .orElseThrow(() -> new NoSuchElementException("Регион не найден: " + regionCodeOrNull));
        }

        return cityRepo.save(City.builder()
                .code(code).nameRu(nameRu).nameEn(nameEn)
                .population(population).region(region)
                .build());
    }

    @Transactional
    public City update(String code, String nameRu, String nameEn, long population) {
        validate(code, nameRu, nameEn, population);

        City c = cityRepo.findByCode(code).orElseThrow(() -> new NoSuchElementException("Город не найден: " + code));

        c.setNameRu(nameRu);
        c.setNameEn(nameEn);
        c.setPopulation(population);

        return c;
    }

    @Transactional
    public String relinkToRegion(String cityCode, String regionCodeOrNull) {
        City c = cityRepo.findByCode(cityCode)
                .orElseThrow(() -> new NoSuchElementException("Город не найден: " + cityCode));

        String old = (c.getRegion() != null ? c.getRegion().getCode() : "null");

        if (regionCodeOrNull == null || regionCodeOrNull.isBlank()) {
            c.setRegion(null);
        } else {
            Region r = regionRepo.findByCode(regionCodeOrNull)
                    .orElseThrow(() -> new NoSuchElementException("Регион не найден: " + regionCodeOrNull));
            c.setRegion(r);
        }

        String now = (c.getRegion() != null ? c.getRegion().getCode() : "null");

        return "Город %s: регион %s -> %s".formatted(c.getCode(), old, now);
    }

    @Transactional
    public boolean deleteByCode(String code) {
        City c = cityRepo.findByCode(code).orElse(null);

        if (c == null) {
            return false;
        }

        cityRepo.delete(c);

        return true;
    }

    @Transactional(readOnly = true)
    public List<City> listAll() {
        return cityRepo.findAllByOrderByNameRuAsc();
    }

    @Transactional(readOnly = true)
    public List<City> listByRegion(String regionCode) {
        Region r = regionRepo.findByCode(regionCode).orElseThrow(() -> new NoSuchElementException("Регион не найден: " + regionCode));

        return cityRepo.findByRegion_IdOrderByNameRuAsc(r.getId());
    }

    public City findByCodeOrThrow(String code){
        return cityRepo.findByCode(code).orElseThrow(() -> new NoSuchElementException("Город не найден: " + code));
    }

    private void validate(String code, String nameRu, String nameEn, long population) {
        if (isBlank(code) || isBlank(nameRu) || isBlank(nameEn)) {
            throw new IllegalArgumentException("Код и имена не должны быть пустыми");
        }

        if (population < 0) {
            throw new IllegalArgumentException("Численность не может быть отрицательной");
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}
