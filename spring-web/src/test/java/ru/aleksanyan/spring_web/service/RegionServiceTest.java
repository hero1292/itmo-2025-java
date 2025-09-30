package ru.aleksanyan.spring_web.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import ru.aleksanyan.spring_web.domain.Region;
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
class RegionServiceTest {

    @Mock RegionRepository regionRepo;
    @InjectMocks RegionService service;

    @Test
    @DisplayName("create: happy path")
    void create_ok() {
        when(regionRepo.existsByCode("ru-c")).thenReturn(false);
        when(regionRepo.save(any(Region.class))).thenAnswer(inv -> inv.getArgument(0));
        Region r = service.create("ru-c","Центр","Center");
        assertThat(r.getCode()).isEqualTo("ru-c");
    }

    @Test
    @DisplayName("create: duplicate")
    void create_duplicate() {
        when(regionRepo.existsByCode("ru-c")).thenReturn(true);
        assertThatThrownBy(() -> service.create("ru-c","Центр","Center"))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    @DisplayName("update: not found")
    void update_notFound() {
        when(regionRepo.findByCode("x")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update("x","a","b"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("list/byCode/delete")
    void reads_ok() {
        when(regionRepo.findAll()).thenReturn(List.of(new Region()));
        assertThat(service.list()).hasSize(1);

        when(regionRepo.findByCode("ru-c")).thenReturn(Optional.of(new Region()));
        assertThat(service.byCode("ru-c")).isPresent();

        when(regionRepo.findByCode("ru-d")).thenReturn(Optional.of(Region.builder().code("ru-d").build()));
        assertThat(service.delete("ru-d")).isTrue();
        verify(regionRepo).delete(any());
    }
}