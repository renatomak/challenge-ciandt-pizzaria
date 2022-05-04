package com.ciandt.pizzaria.controllers;

import com.ciandt.pizzaria.dtos.FlavorDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Api(value = "Flavor", tags = "Flavor")
public interface FlavorApi {

    @ApiOperation(value = "Get a paginated list of registered flavors", tags = "Flavor")
    public ResponseEntity<Page<FlavorDto>> findAll(Pageable pageable);

    @ApiOperation(value = "Get a registered flavor by entering the ID", tags = "Flavor")
    public ResponseEntity<FlavorDto> findById(@PathVariable Long id);

    @ApiOperation(value = "Create a new flavor", tags = "Flavor")
    public ResponseEntity<FlavorDto> create(@RequestBody FlavorDto flavorDto);

    @ApiOperation(value = "Update a flavor by entering the ID", tags = "Flavor")
    public ResponseEntity<FlavorDto> update(@PathVariable Long id, @RequestBody FlavorDto flavorDto);

    @ApiOperation(value = "Delete a registered flavor by entering the ID", tags = "Flavor")
    public ResponseEntity<Void> delete(@PathVariable Long id);
}
