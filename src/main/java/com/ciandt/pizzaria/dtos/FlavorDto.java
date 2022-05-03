package com.ciandt.pizzaria.dtos;

import com.ciandt.pizzaria.models.Flavor;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FlavorDto implements Serializable {
    private static final long serialVersionUID = 7193684038002132400L;

    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    private String description;

    private Double price;

    public FlavorDto(Flavor entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
    }
}
