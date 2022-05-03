package com.ciandt.pizzaria.repositories;

import com.ciandt.pizzaria.models.Flavor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlavorRepository extends JpaRepository<Flavor, Long> {
}
