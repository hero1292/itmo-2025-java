package ru.aleksanyan.spring_database.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aleksanyan.spring_database.dao.CityDao;
import ru.aleksanyan.spring_database.dao.RegionDao;
import ru.aleksanyan.spring_database.domain.City;
import ru.aleksanyan.spring_database.domain.Region;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class CityService {
    private final CityDao cityDao;
    private final RegionDao regionDao;

    public City create(String code, String nameRu, String nameEn, long population, String regionCodeOrNull) {
        validate(code, nameRu, nameEn, population);

        if (cityDao.findByCode(code).isPresent())
            throw new DuplicateKeyException("Город с кодом уже существует: " + code);

        Long regionId = null;

        if (regionCodeOrNull != null && !regionCodeOrNull.isBlank()) {
            regionId = regionDao.findByCode(regionCodeOrNull).orElseThrow(() -> new NoSuchElementException("Регион не найден: " + regionCodeOrNull)).getId();
        }

        return cityDao.create(City.builder().code(code).nameRu(nameRu).nameEn(nameEn).population(population).regionId(regionId).build());
    }

    public City relinkToRegion(String cityCode, String regionCode) {
        City city = cityDao.findByCode(cityCode).orElseThrow(() -> new NoSuchElementException("Город не найден"));
        Region region = regionDao.findByCode(regionCode).orElseThrow(() -> new NoSuchElementException("Регион не найден"));

        city.setRegionId(region.getId());

        return cityDao.update(city);
    }

    public City update(String code, String nameRu, String nameEn, long population) {
        City city = cityDao.findByCode(code).orElseThrow(() -> new NoSuchElementException("Город не найден"));

        validate(code, nameRu, nameEn, population);

        city.setNameRu(nameRu);
        city.setNameEn(nameEn);
        city.setPopulation(population);

        return cityDao.update(city);
    }

    public boolean deleteByCode(String code) {
        return cityDao.findByCode(code).map(c -> cityDao.deleteById(c.getId())).orElse(false);
    }

    public List<City> listAll() {
        return cityDao.findAll();
    }

    public List<City> listByRegion(String regionCode) {
        Region region = regionDao.findByCode(regionCode).orElseThrow(() -> new NoSuchElementException("Регион не найден"));

        return cityDao.findByRegionId(region.getId());
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
