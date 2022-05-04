package com.ciandt.pizzaria.services;

import com.ciandt.pizzaria.dtos.FlavorDto;
import com.ciandt.pizzaria.models.Flavor;
import com.ciandt.pizzaria.repositories.FlavorRepository;
import com.ciandt.pizzaria.services.exceptions.DataBasesException;
import com.ciandt.pizzaria.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static com.ciandt.pizzaria.utils.Messages.*;
import static java.util.Objects.isNull;

@Service
public class FlavorService {
    
    @Autowired
    private FlavorRepository flavorRepository;

    @Transactional(readOnly = true)
    public Page<FlavorDto> findAllPaged(Pageable pageable) {
        Page<Flavor> flavors = flavorRepository.findAll(pageable);
        return flavors.map(FlavorDto::new);
    }

    @Transactional(readOnly = true)
    public FlavorDto findById(Long id) {
        Optional<Flavor> flavor = flavorRepository.findById(id);
        Flavor entity = flavor.orElseThrow(() -> new ResourceNotFoundException(EXCEPTION_ENTITY_NOT_FOUND));
        return new FlavorDto(entity);
    }

    @Transactional
    public FlavorDto create(FlavorDto dto) {
        Flavor entity = new Flavor();
        copyDtoToEntity(dto, entity);
        entity = flavorRepository.save(entity);
        return new FlavorDto(entity);
    }

    @Transactional
    public FlavorDto update(Long id, FlavorDto dto) {
        try {
            Flavor entity = flavorRepository.getById(id);
            copyDtoToEntityUpdate(dto, entity);
            entity = flavorRepository.save(entity);
            return new FlavorDto(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(EXCEPTION_ID_NOT_FOUND + id);
        }
    }

    public void delete(Long id) {
        try {
            flavorRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(EXCEPTION_ID_NOT_FOUND + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBasesException(EXCEPTION_INTEGRITY_VIOLATION);
        }
    }

    private void copyDtoToEntity(FlavorDto dto, Flavor entity) {
        validationFields(dto);
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
    }


    private void copyDtoToEntityUpdate(FlavorDto dto, Flavor entity) {
        if (!isNull(dto.getName())) {
            validName(dto.getName());
            entity.setName(dto.getName());
        }
        if (!isNull(dto.getDescription())) {
            validDescription(dto.getDescription());
            entity.setDescription(dto.getDescription());
        }
        if (!isNull(dto.getPrice())) {
            validPrice(dto.getPrice());
            entity.setPrice(dto.getPrice());
        }
    }

    private void validName(String field) {
        if (field.trim().isEmpty()) {
            throw new ResourceNotFoundException(VALIDATION_NAME_IS_EMPTY);
        }
        if (field.trim().length() < 3 || field.trim().length() > 240) {
            throw new ResourceNotFoundException(VALIDATION_NAME_SIZE);
        }
    }

    private void validDescription(String field) {
        if (field.trim().isEmpty()) {
            throw new ResourceNotFoundException(VALIDATION_DESCRIPTION_IS_EMPTY);
        }
        if (field.trim().length() > 240) {
            throw new ResourceNotFoundException(VALIDATION_DESCRIPTION_SIZE);
        }
    }

    private void validPrice(Double price) {
        if (price < 1) {
            throw new ResourceNotFoundException(VALIDATION_PRICE_GREATER_THAN_ONE);
        }
    }

    private void validFieldsIsNotNull(FlavorDto dto) {
        if (isNull(dto.getName())) {
            throw new ResourceNotFoundException(VALIDATION_NAME_IS_REQUIRED);
        }
        if (isNull(dto.getDescription())) {
            throw new ResourceNotFoundException(VALIDATION_DESCRIPTION_IS_REQUIRED);
        }
        if (isNull(dto.getPrice())) {
            throw new ResourceNotFoundException(VALIDATION_PRICE_IS_REQUIRED);
        }
    }

    private void validationFields(FlavorDto dto) {
        validFieldsIsNotNull(dto);
        validName(dto.getName());
        validDescription(dto.getDescription());
        validPrice(dto.getPrice());
    }
}
