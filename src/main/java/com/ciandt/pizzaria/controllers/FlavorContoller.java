package com.ciandt.pizzaria.controllers;

import com.ciandt.pizzaria.dtos.FlavorDto;
import com.ciandt.pizzaria.services.FlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/flavors")
public class FlavorContoller {

    @Autowired
    private FlavorService flavorService;

    @GetMapping
    public ResponseEntity<Page<FlavorDto>> findAll(Pageable pageable) {
        Page<FlavorDto> list = flavorService.findAllPaged(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FlavorDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(flavorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<FlavorDto> create(@RequestBody FlavorDto flavorDto) {
        flavorDto = flavorService.create(flavorDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(flavorDto.getId()).toUri();
        return ResponseEntity.created(uri).body(flavorDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<FlavorDto> update(@PathVariable Long id, @RequestBody FlavorDto flavorDto) {
        return ResponseEntity.ok(flavorService.update(id, flavorDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        flavorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
