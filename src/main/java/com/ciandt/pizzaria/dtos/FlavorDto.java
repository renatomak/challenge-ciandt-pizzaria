package com.ciandt.pizzaria.dtos;

import com.ciandt.pizzaria.models.Flavor;
import com.ciandt.pizzaria.utils.Messages;
import lombok.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
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

    @NotEmpty(message = Messages.VALIDATION_NAME_IS_REQUIRED)
    private String name;

    @NotEmpty(message = Messages.VALIDATION_DESCRIPTION_IS_REQUIRED)
    private String description;

    @NotEmpty(message = Messages.VALIDATION_PRICE_IS_REQUIRED)
    @Digits(integer = 6, fraction = 2)
    private Double price;

    public FlavorDto(Flavor entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
    }
}
