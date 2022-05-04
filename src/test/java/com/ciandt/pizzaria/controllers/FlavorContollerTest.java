package com.ciandt.pizzaria.controllers;

import com.ciandt.pizzaria.dtos.FlavorDto;
import com.ciandt.pizzaria.services.FlavorService;
import com.ciandt.pizzaria.services.exceptions.ResourceNotFoundException;
import com.ciandt.pizzaria.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FlavorContoller.class)
class FlavorContollerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlavorService flavorService;

    @Autowired
    private ObjectMapper objectMapper;

    private Long existsId;
    private Long nonExistsId;
    private FlavorDto flavorDto;


    @BeforeEach
    void setUp() {
        existsId = 1L;
        nonExistsId = 2L;
        flavorDto = Factory.createFlavorDto();
        PageImpl<FlavorDto> page = new PageImpl<>(List.of(flavorDto));

        Mockito.when(flavorService.findAllPaged(Mockito.any())).thenReturn(page);

        Mockito.when(flavorService.findById(existsId)).thenReturn(flavorDto);
        Mockito.when(flavorService.findById(nonExistsId)).thenThrow(ResourceNotFoundException.class);

        Mockito.when(flavorService.create(flavorDto)).thenReturn(flavorDto);

        Mockito.when(flavorService.update(existsId, flavorDto)).thenReturn(flavorDto);
        Mockito.when(flavorService.update(nonExistsId, flavorDto)).thenThrow(ResourceNotFoundException.class);

        doNothing().when(flavorService).delete(existsId);
        Mockito.doThrow(ResourceNotFoundException.class).when(flavorService).delete(nonExistsId);
    }

    @Test
    void findAllShouldReturnPage() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/flavors")
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findByIdShouldReturnFlavorWhenIdExixts() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/flavors/{id}", existsId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExixts() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/flavors/{id}", nonExistsId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

    @Test
    void createShouldReturnFlavorDto() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(flavorDto);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/flavors")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isCreated());
    }

    @Test
    void updateShouldReturnFlavorDtoWhenIdExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(flavorDto);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/flavors/{id}", existsId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    void updateShouldNotFoundWhenIdDoesNotExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(flavorDto);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/flavors/{id}", nonExistsId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

    @Test
    void deleteShouldReturnNoContentWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/flavors/{id}", existsId));
        result.andExpect(status().isNoContent());
    }

    @Test
    void deleteShouldNotFoundWhenIdDoesNotExists() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/flavors/{id}", nonExistsId));
        result.andExpect(status().isNotFound());
    }
}