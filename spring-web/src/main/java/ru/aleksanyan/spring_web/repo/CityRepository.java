package ru.aleksanyan.spring_web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aleksanyan.spring_web.domain.City;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByCode(String code);
    boolean existsByCode(String code);
    List<City> findByRegion_IdOrderByNameRuAsc(Long regionId);     // по региону
    List<City> findAllByOrderByNameRuAsc();                        // все
}