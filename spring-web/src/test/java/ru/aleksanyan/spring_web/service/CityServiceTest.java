package ru.aleksanyan.spring_web.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import ru.aleksanyan.spring_web.domain.City;
import ru.aleksanyan.spring_web.domain.Region;
import ru.aleksanyan.spring_web.repo.CityRepository;
import ru.aleksanyan.spring_web.repo.RegionRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock CityRepository cityRepo;
    @Mock RegionRepository regionRepo;

    @InjectMocks CityService service;

    @Test
    @DisplayName("create: happy path без региона")
    void create_ok_withoutRegion() {
        when(cityRepo.existsByCode("msc")).thenReturn(false);
        when(cityRepo.save(any(City.class))).thenAnswer(inv -> {
            City c = inv.getArgument(0);
            c.setId(1L);
            return c;
        });

        City c = service.create("msc","Москва","Moscow", 12_500_000, null);

        assertThat(c.getId()).isEqualTo(1L);
        assertThat(c.getRegion()).isNull();

        ArgumentCaptor<City> captor = ArgumentCaptor.forClass(City.class);
        verify(cityRepo).save(captor.capture());
        assertThat(captor.getValue().getCode()).isEqualTo("msc");
    }

    @Test
    @DisplayName("create: с привязкой к региону")
    void create_ok_withRegion() {
        when(cityRepo.existsByCode("spb")).thenReturn(false);
        when(regionRepo.findByCode("ru-nw")).thenReturn(Optional.of(Region.builder().id(10L).code("ru-nw").build()));
        when(cityRepo.save(any(City.class))).thenAnswer(inv -> inv.getArgument(0));

        City c = service.create("spb","Санкт-Петербург","Saint-Petersburg", 5_600_000, "ru-nw");

        assertThat(c.getRegion()).isNotNull();
        assertThat(c.getRegion().getCode()).isEqualTo("ru-nw");
    }

    @Test
    @DisplayName("create: дубль кода -> DuplicateKeyException")
    void create_duplicate() {
        when(cityRepo.existsByCode("msc")).thenReturn(true);
        assertThatThrownBy(() -> service.create("msc","Москва","Moscow",1,"ru-c"))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    @DisplayName("create: регион не найден -> NoSuchElementException")
    void create_regionMissing() {
        when(cityRepo.existsByCode("msc")).thenReturn(false);
        when(regionRepo.findByCode("x")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create("msc","Москва","Moscow",1,"x"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Регион не найден");
    }

    @Test
    @DisplayName("update: обновляет имена и население")
    void update_ok() {
        City existed = City.builder().code("msc").nameRu("Москва").nameEn("Moscow").population(1).build();
        when(cityRepo.findByCode("msc")).thenReturn(Optional.of(existed));

        City updated = service.update("msc","Москва-2","Moscow-2",100);

        assertThat(updated.getNameRu()).isEqualTo("Москва-2");
        assertThat(updated.getPopulation()).isEqualTo(100);
    }

    @Test
    @DisplayName("update: город не найден")
    void update_notFound() {
        when(cityRepo.findByCode("msc")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update("msc","a","b",1))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("relinkToRegion: снятие привязки и привязка")
    void relink_ok() {
        City c = City.builder().code("msc").build();
        when(cityRepo.findByCode("msc")).thenReturn(Optional.of(c));
        when(regionRepo.findByCode("ru-c")).thenReturn(Optional.of(Region.builder().code("ru-c").build()));

        String msg1 = service.relinkToRegion("msc", null);
        assertThat(msg1).contains("null");

        String msg2 = service.relinkToRegion("msc", "ru-c");
        assertThat(c.getRegion()).isNotNull();
        assertThat(msg2).contains("ru-c");
    }

    @Test
    @DisplayName("listAll / listByRegion / findByCodeOrThrow")
    void reads_ok() {
        when(cityRepo.findAllByOrderByNameRuAsc()).thenReturn(List.of(new City(), new City()));
        assertThat(service.listAll()).hasSize(2);

        when(regionRepo.findByCode("ru-c")).thenReturn(Optional.of(Region.builder().id(1L).code("ru-c").build()));
        when(cityRepo.findByRegion_IdOrderByNameRuAsc(1L)).thenReturn(List.of(new City()));
        assertThat(service.listByRegion("ru-c")).hasSize(1);

        when(cityRepo.findByCode("msc")).thenReturn(Optional.of(new City()));
        assertThat(service.findByCodeOrThrow("msc")).isNotNull();
    }
}