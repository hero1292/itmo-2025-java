package ru.aleksanyan.spring_database.dao;

import ru.aleksanyan.spring_database.domain.Region;

import java.util.List;
import java.util.Optional;

public interface RegionDao {
    Region create(Region region);
    Optional<Region> findById(Long id);
    Optional<Region> findByCode(String code);
    List<Region> findAll();
    Region update(Region region);
    boolean deleteById(Long id);
}
