package ru.aleksanyan.spring_web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aleksanyan.spring_web.domain.Region;
import ru.aleksanyan.spring_web.repo.RegionRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegionService {
    private final RegionRepository regionRepo;

    @Transactional
    public Region create(String code, String nameRu, String nameEn) {
        validate(code, nameRu, nameEn);

        if (regionRepo.existsByCode(code)) {
            throw new DuplicateKeyException("Регион уже существует: " + code);
        }

        return regionRepo.save(Region.builder().code(code).nameRu(nameRu).nameEn(nameEn).build());
    }

    @Transactional
    public Region update(String code, String nameRu, String nameEn) {
        validate(code, nameRu, nameEn);

        Region r = regionRepo.findByCode(code).orElseThrow(() -> new NoSuchElementException("Регион не найден: " + code));

        r.setNameRu(nameRu);
        r.setNameEn(nameEn);

        return r;
    }

    @Transactional(readOnly = true)
    public List<Region> list() {
        return regionRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Region> byCode(String code) {
        return regionRepo.findByCode(code);
    }

    @Transactional
    public boolean delete(String code) {
        Region r = regionRepo.findByCode(code).orElseThrow(() -> new NoSuchElementException("Регион не найден: " + code));

        regionRepo.delete(r);

        return true;
    }

    private void validate(String code, String nameRu, String nameEn) {
        if (isBlank(code) || isBlank(nameRu) || isBlank(nameEn)) {
            throw new IllegalArgumentException("Код и имена не должны быть пустыми");
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}