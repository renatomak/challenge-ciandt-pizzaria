package com.ciandt.pizzaria.models;

import com.ciandt.pizzaria.dtos.FlavorDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static com.ciandt.pizzaria.utils.Messages.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "tb_flavor")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Flavor implements Serializable {
    private static final long serialVersionUID = -8194331057711583877L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = VALIDATION_NAME_IS_EMPTY)
    @NotNull(message = VALIDATION_NAME_IS_REQUIRED)
    @Size(min = 3, max = 100, message = VALIDATION_NAME_SIZE)
    private String name;

    @NotEmpty(message = VALIDATION_DESCRIPTION_IS_EMPTY)
    @NotNull(message = VALIDATION_DESCRIPTION_IS_REQUIRED)
    @Size(max = 240, message = VALIDATION_DESCRIPTION_SIZE)
    private String description;

    @Column(nullable = false)
    @DecimalMin(value = "1.00", message = VALIDATION_PRICE_GREATER_THAN_ONE)
    private Double price;

    public Flavor(FlavorDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.price = dto.getPrice();
    }
}
