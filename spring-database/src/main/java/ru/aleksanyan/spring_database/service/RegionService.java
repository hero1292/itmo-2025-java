package ru.aleksanyan.spring_database.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aleksanyan.spring_database.dao.RegionDao;
import ru.aleksanyan.spring_database.domain.Region;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegionService {
    private final RegionDao regionDao;

    public Region create(String code, String nameRu, String nameEn) {
        validate(code, nameRu, nameEn);

        if (regionDao.findByCode(code).isPresent()) {
            throw new DuplicateKeyException("Регион с кодом уже существует: " + code);
        }

        return regionDao.create(Region.builder().code(code).nameRu(nameRu).nameEn(nameEn).build());
    }

    public Region update(Long id, String code, String nameRu, String nameEn) {
        validate(code, nameRu, nameEn);

        Region existing = regionDao.findById(id).orElseThrow(() -> new NoSuchElementException("Регион не найден"));

        existing.setCode(code);
        existing.setNameRu(nameRu);
        existing.setNameEn(nameEn);

        return regionDao.update(existing);
    }

    public List<Region> list() {
        return regionDao.findAll();
    }

    public Optional<Region> byCode(String code) {
        return regionDao.findByCode(code);
    }

    public boolean delete(Long id) {
        return regionDao.deleteById(id);
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
