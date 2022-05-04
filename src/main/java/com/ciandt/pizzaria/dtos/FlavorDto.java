package com.ciandt.pizzaria.dtos;

import com.ciandt.pizzaria.models.Flavor;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static com.ciandt.pizzaria.utils.Messages.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FlavorDto implements Serializable {
    private static final long serialVersionUID = 7193684038002132400L;

    @EqualsAndHashCode.Include
    private Long id;

    @NotEmpty(message = VALIDATION_NAME_IS_EMPTY)
    @NotNull(message = VALIDATION_NAME_IS_REQUIRED)
    @Size(min = 3, max = 100, message = VALIDATION_NAME_SIZE)
    private String name;

    @NotEmpty(message = VALIDATION_DESCRIPTION_IS_EMPTY)
    @NotNull(message = VALIDATION_DESCRIPTION_IS_REQUIRED)
    @Size(max = 240, message = VALIDATION_DESCRIPTION_SIZE)
    private String description;

    @NotEmpty(message = VALIDATION_PRICE_IS_REQUIRED)
    private Double price;

    public FlavorDto(Flavor entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
    }
}
