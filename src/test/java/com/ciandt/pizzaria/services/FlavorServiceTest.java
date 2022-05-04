package com.ciandt.pizzaria.services;

import com.ciandt.pizzaria.dtos.FlavorDto;
import com.ciandt.pizzaria.models.Flavor;
import com.ciandt.pizzaria.repositories.FlavorRepository;
import com.ciandt.pizzaria.services.exceptions.ResourceNotFoundException;
import com.ciandt.pizzaria.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.ciandt.pizzaria.utils.Messages.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class FlavorServiceTest {

    @InjectMocks
    private FlavorService flavorService;

    @Mock
    private FlavorRepository flavorRepository;

    private Flavor flavor;
    private Long existsId;
    private Long nonExistsId;

    private FlavorDto flavorDto;

    private final String expectedName = "Grega";
    private final String expectedDescription = "Molho de tomate, mussarela, presunto picado, cebola, ovos e orégano.";
    private final Double expectedPrice = 25.00;

    @BeforeEach
    void setUp() {
        existsId = 1L;
        nonExistsId = 2L;
        flavorDto = Factory.createFlavorDto();
        flavor = Factory.createFlavor();
        Flavor flavorWithId = Factory.createFlavorWithId();
        PageImpl<Flavor> page = new PageImpl<>(List.of(flavor));

        Mockito.when(flavorRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        Mockito.when(flavorRepository.findById(existsId)).thenReturn(Optional.of(flavor));
        Mockito.when(flavorRepository.findById(nonExistsId)).thenThrow(ResourceNotFoundException.class);

        Mockito.when(flavorRepository.save(flavor)).thenReturn(flavorWithId);

        Mockito.when(flavorRepository.getById(existsId)).thenReturn(flavor);
        Mockito.when(flavorRepository.getById(nonExistsId)).thenThrow(ResourceNotFoundException.class);

        doNothing().when(flavorRepository).deleteById(existsId);
        doThrow(ResourceNotFoundException.class).when(flavorRepository).deleteById(nonExistsId);
    }


    @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        // Act
        Assertions.assertThrows(ResourceNotFoundException.class, () -> flavorService.delete(nonExistsId));

        // Assert
        verify(flavorRepository, times(1)).deleteById(nonExistsId);
    }

    @Test
    void deleteShouldDoNothingWhenIdExists() {
        // Act
        Assertions.assertDoesNotThrow(() -> flavorService.delete(existsId));

        // Assert
        verify(flavorRepository, times(1)).deleteById(existsId);
    }

    @Test
    void updateShouldReturnFlavorDtoWhenIdExists() {
        // Act
        FlavorDto result = flavorService.update(existsId, flavorDto);

        // Assert
        Assertions.assertNotNull(result);
        verify(flavorRepository, times(1)).save(flavor);
        Assertions.assertEquals(result.getName(), expectedName);
        Assertions.assertEquals(result.getDescription(), expectedDescription);
        Assertions.assertEquals(result.getPrice(), expectedPrice);
    }

    @Test
    void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
       var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> flavorService.update(nonExistsId, flavorDto));

        // Assert
        verify(flavorRepository, times(1)).getById(nonExistsId);
        Assertions.assertEquals(result.getClass(), ResourceNotFoundException.class);
    }

    @Test
    void updateShouldReturnResourceNotFoundExceptionWhenNameSizeLessThanThree() {
        // Arrange
        FlavorDto dtoNameTest = flavorDto;
        dtoNameTest.setName("Mu");

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> flavorService.update(existsId, dtoNameTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_NAME_SIZE);
    }

    @Test
    void updateShouldReturnResourceNotFoundExceptionWhenNameIsEmpty() {
        // Arrange
        FlavorDto dtoNameTest = flavorDto;
        dtoNameTest.setName(" ");

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> flavorService.update(existsId, dtoNameTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_NAME_IS_EMPTY);
    }

    @Test
    void updateShouldReturnResourceNotFoundExceptionWhenNameSizeGreaterThan100() {
        // Arrange
        FlavorDto dtoNameTest = flavorDto;
        dtoNameTest.setName("Apetitosa apetitosa apetitosa apetitosa apetitosa apetitosa apetitosa apetitosa apetitosa apetitosa a");

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> flavorService.update(existsId, dtoNameTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_NAME_SIZE);
    }

    @Test
    void updateShouldReturnResourceNotFoundExceptionWhenDescriptionSizeLessThanThree() {
        // Arrange
        FlavorDto dtoDescriptionTest = flavorDto;
        dtoDescriptionTest.setDescription("Ma");

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> flavorService.update(existsId, dtoDescriptionTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_DESCRIPTION_SIZE);
    }

    @Test
    void updateShouldReturnResourceNotFoundExceptionWhenDescriptionSizeGreaterThan240() {
        // Arrange
        FlavorDto dtoDescriptionTest = flavorDto;
        dtoDescriptionTest.setDescription("Molho de tomate, mussarela, calabresa, frango, milho, palmito, catupiri e orégano, mussarela, calabresa, frango, milho, palmito, catupiri e orégano, mussarela, calabresa, frango, milho, palmito, catupiri e orégano,mussarela, calabresa, frango.");

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> flavorService.update(existsId, dtoDescriptionTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_DESCRIPTION_SIZE);
    }

    @Test
    void updateShouldReturnResourceNotFoundExceptionWhenPriceIsZero() {
        // Arrange
        FlavorDto dtoPriceTest = flavorDto;
        dtoPriceTest.setPrice(0.00);

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> flavorService.update(existsId, dtoPriceTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_PRICE_GREATER_THAN_ONE);
    }

    @Test
    void createShouldReturnFlavorDto() {
        // Act
        FlavorDto result = flavorService.create(flavorDto);

        // Assert
        Assertions.assertNotNull(result);
        verify(flavorRepository, times(1)).save(flavor);
        Assertions.assertEquals(result.getName(), expectedName);
        Assertions.assertEquals(result.getDescription(), expectedDescription);
        Assertions.assertEquals(result.getPrice(), expectedPrice);
    }

    @Test
    void createShouldReturnResourceNotFoundExceptionWhenNameIsNull() {
        // Arrange
        FlavorDto dtoNameNull = flavorDto;
        dtoNameNull.setName(null);

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> flavorService.create(dtoNameNull));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_NAME_IS_REQUIRED);
    }

    @Test
    void createShouldReturnResourceNotFoundExceptionWhenNameIsEmpty() {
        // Arrange
        FlavorDto dtoNameTest = flavorDto;
        dtoNameTest.setName(" ");

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> flavorService.create(dtoNameTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_NAME_IS_EMPTY);
    }

    @Test
    void createShouldReturnResourceNotFoundExceptionWhenNameSizeLessThanThree() {
        // Arrange
        FlavorDto dtoNameTest = flavorDto;
        dtoNameTest.setName("Mu");

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> flavorService.create(dtoNameTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_NAME_SIZE);
    }

    @Test
    void createShouldReturnResourceNotFoundExceptionWhenNameSizeGreaterThan100() {
        // Arrange
        FlavorDto dtoNameTest = flavorDto;
        dtoNameTest.setName("Apetitosa apetitosa apetitosa apetitosa apetitosa apetitosa apetitosa apetitosa apetitosa apetitosa a");

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> flavorService.create(dtoNameTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_NAME_SIZE);
    }

    @Test
    void createShouldReturnResourceNotFoundExceptionWhenDescriptionSizeLessThanThree() {
        // Arrange
        FlavorDto dtoDescriptionTest = flavorDto;
        dtoDescriptionTest.setDescription("Ma");

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> flavorService.create(dtoDescriptionTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_DESCRIPTION_SIZE);
    }

    @Test
    void createShouldReturnResourceNotFoundExceptionWhenDescriptionSizeGreaterThan240() {
        // Arrange
        FlavorDto dtoDescriptionTest = flavorDto;
        dtoDescriptionTest.setDescription("Molho de tomate, mussarela, calabresa, frango, milho, palmito, catupiri e orégano, mussarela, calabresa, frango, milho, palmito, catupiri e orégano, mussarela, calabresa, frango, milho, palmito, catupiri e orégano,mussarela, calabresa, frango.");

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> flavorService.create(dtoDescriptionTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_DESCRIPTION_SIZE);
    }

    @Test
    void createShouldReturnResourceNotFoundExceptionWhenDescriptionIsNull() {
        // Arrange
        FlavorDto dtoDescriptionTest = flavorDto;
        dtoDescriptionTest.setDescription(null);

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> flavorService.create(dtoDescriptionTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_DESCRIPTION_IS_REQUIRED);
    }

    @Test
    void createShouldReturnResourceNotFoundExceptionWhenPriceIsZero() {
        // Arrange
        FlavorDto dtoPriceTest = flavorDto;
        dtoPriceTest.setPrice(0.00);

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> flavorService.create(dtoPriceTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_PRICE_GREATER_THAN_ONE);
    }

    @Test
    void createShouldReturnResourceNotFoundExceptionWhenPriceIsNull() {
        // Arrange
        FlavorDto dtoPriceTest = flavorDto;
        dtoPriceTest.setPrice(null);

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> flavorService.create(dtoPriceTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_PRICE_IS_REQUIRED);
    }

    @Test
    public void findAllShouldReturnPage() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<FlavorDto> result = flavorService.findAllPaged(pageable);

        // Assert
        Assertions.assertNotNull(result);
        verify(flavorRepository, times(1)).findAll(pageable);
    }

    @Test
    void findByIdShouldReturnFlavorDtoWhenIdExixts() {
        // Act
        FlavorDto result = flavorService.findById(existsId);

        // Assert
        Assertions.assertNotNull(result);
        verify(flavorRepository, times(1)).findById(existsId);
        Assertions.assertEquals(result.getName(), expectedName);
        Assertions.assertEquals(result.getDescription(), expectedDescription);
        Assertions.assertEquals(result.getPrice(), expectedPrice);
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExixts() {
        // Assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> flavorService.findById(nonExistsId));

        verify(flavorRepository, times(1)).findById(nonExistsId);
    }

}