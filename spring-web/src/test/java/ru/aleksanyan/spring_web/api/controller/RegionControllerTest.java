package ru.aleksanyan.spring_web.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.aleksanyan.spring_web.api.dto.RegionDto;
import ru.aleksanyan.spring_web.api.error.GlobalExceptionHandler;
import ru.aleksanyan.spring_web.api.mapper.RegionMapper;
import ru.aleksanyan.spring_web.domain.Region;
import ru.aleksanyan.spring_web.service.RegionService;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = RegionController.class)
@Import({GlobalExceptionHandler.class, RegionMapper.class})
class RegionControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    @MockitoBean RegionService service;

    @Test
    @DisplayName("GET /api/regions — публичный")
    void list_public() throws Exception {
        Region r = Region.builder().id(1L).code("ru-c").nameRu("Центр").nameEn("Center").build();
        Mockito.when(service.list()).thenReturn(List.of(r));

        mvc.perform(get("/api/regions").with(user("u").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].code", is("ru-c")));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("POST /api/regions — ADMIN")
    void create_admin() throws Exception {
        RegionDto dto = new RegionDto("ru-c","Центр","Center");
        Region r = Region.builder().id(1L).code("ru-c").nameRu("Центр").nameEn("Center").build();
        Mockito.when(service.create("ru-c","Центр","Center")).thenReturn(r);

        mvc.perform(post("/api/regions")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("ru-c")));
    }

    @Test
    @DisplayName("POST /api/regions без роли — 401/403")
    void create_forbidden() throws Exception {
        RegionDto dto = new RegionDto("ru-c","Центр","Center");
        mvc.perform(post("/api/regions").contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status().is4xxClientError());
    }
}