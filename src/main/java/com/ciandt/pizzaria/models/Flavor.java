package com.ciandt.pizzaria.models;

import com.ciandt.pizzaria.dtos.FlavorDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@Table(name = "tb_flavor")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Flavor implements Serializable {
    private static final long serialVersionUID = -8194331057711583877L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

    public Flavor(FlavorDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.price = dto.getPrice();
    }

}
