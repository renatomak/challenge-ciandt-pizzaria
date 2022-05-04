package com.ciandt.pizzaria.services;

import com.ciandt.pizzaria.dtos.FlavorDto;
import com.ciandt.pizzaria.repositories.FlavorRepository;
import com.ciandt.pizzaria.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class FlavorServiceTestIT {

    @Autowired
    private FlavorService flavorService;

    @Autowired
    private FlavorRepository flavorRepository;

    private long existingId;
    private long nonExistingId;

    private int countTotalFlavors;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalFlavors = 6;
    }

    @Test
    void deleteShouldDeleteResourceWhenIdExists() {
        flavorService.delete(existingId);

        Assertions.assertEquals(countTotalFlavors - 1, flavorRepository.count());
    }

    @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> flavorService.delete(nonExistingId));
    }

    @Test void findAllPagedShouldReturnPageWhenPage0Size10() {
        PageRequest pageRequest = PageRequest.of(0, 10);

       Page<FlavorDto> result =  flavorService.findAllPaged(pageRequest);

       Assertions.assertFalse(result.isEmpty());
       Assertions.assertEquals(0, result.getNumber());
       Assertions.assertEquals(10, result.getSize());
       Assertions.assertEquals(countTotalFlavors, result.getTotalElements());
    }

    @Test void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist() {
        PageRequest pageRequest = PageRequest.of(50, 10);

       Page<FlavorDto> result =  flavorService.findAllPaged(pageRequest);

       Assertions.assertTrue(result.isEmpty());
    }

    @Test void findAllPagedShouldReturnSortedPageWhenSortByName() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));

       Page<FlavorDto> result =  flavorService.findAllPaged(pageRequest);

       Assertions.assertFalse(result.isEmpty());
       Assertions.assertEquals("Acebolada", result.getContent().get(0).getName());
       Assertions.assertEquals("Alho e Óleo", result.getContent().get(1).getName());
       Assertions.assertEquals("Americana", result.getContent().get(2).getName());
    }
}