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
        Flavor entity = flavor.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
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
            copyDtoToEntity(dto, entity);
            entity = flavorRepository.save(entity);
            return new FlavorDto(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found" + id);
        }
    }

    public void delete(Long id) {
        try {
            flavorRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBasesException("Integrity violation");
        }
    }

    private void copyDtoToEntity(FlavorDto dto, Flavor entity) {
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
    }
}
