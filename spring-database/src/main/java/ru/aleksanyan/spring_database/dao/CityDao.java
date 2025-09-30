package ru.aleksanyan.spring_database.dao;

import ru.aleksanyan.spring_database.domain.City;

import java.util.List;
import java.util.Optional;

public interface CityDao {
    City create(City city);
    Optional<City> findById(Long id);
    Optional<City> findByCode(String code);
    List<City> findAll();
    List<City> findByRegionId(Long regionId);
    City update(City city);
    boolean deleteById(Long id);
}