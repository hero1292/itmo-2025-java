package ru.aleksanyan.spring_jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aleksanyan.spring_jpa.domain.Region;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {
    Optional<Region> findByCode(String code);
    boolean existsByCode(String code);
}
