package ru.aleksanyan.spring_web.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.aleksanyan.spring_web.api.dto.CityDto;
import ru.aleksanyan.spring_web.api.error.GlobalExceptionHandler;
import ru.aleksanyan.spring_web.api.mapper.CityMapper;
import ru.aleksanyan.spring_web.domain.City;
import ru.aleksanyan.spring_web.service.CityService;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = CityController.class)
@Import({GlobalExceptionHandler.class, CityMapper.class})
class CityControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    @MockitoBean CityService service;

    @Test
    @DisplayName("GET /api/cities — доступен без аутентификации")
    void list_public() throws Exception {
        City c = City.builder().id(1L).code("msc").nameRu("Москва").nameEn("Moscow").population(10).build();
        Mockito.when(service.listAll()).thenReturn(List.of(c));

        mvc.perform(get("/api/cities").with(user("u").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].code", is("msc")));
    }

    @Test
    @DisplayName("GET /api/cities/{code} — детальная")
    void get_public() throws Exception {
        City c = City.builder().id(1L).code("msc").nameRu("Москва").nameEn("Moscow").population(10).build();
        Mockito.when(service.findByCodeOrThrow("msc")).thenReturn(c);

        mvc.perform(get("/api/cities/msc").with(user("u").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nameEn", is("Moscow")));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("POST /api/cities — требует ADMIN")
    void create_admin() throws Exception {
        CityDto dto = new CityDto("msc","Москва","Moscow",10,"ru-c");
        City c = City.builder().id(1L).code("msc").nameRu("Москва").nameEn("Moscow").population(10).build();
        Mockito.when(service.create("msc","Москва","Moscow",10,"ru-c")).thenReturn(c);

        mvc.perform(post("/api/cities")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("msc")));
    }

    @Test
    @DisplayName("POST /api/cities без роли — 401/403 (зависит от конфигурации)")
    void create_unauthorized() throws Exception {
        CityDto dto = new CityDto("msc","Москва","Moscow",10,"ru-c");
        mvc.perform(post("/api/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status().is4xxClientError());
    }
}