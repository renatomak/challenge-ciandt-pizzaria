package com.ciandt.pizzaria.tests;

import com.ciandt.pizzaria.dtos.FlavorDto;
import com.ciandt.pizzaria.models.Flavor;

public class Factory {
    private static final Long idTest = 1L;
    private static final String nameTest = "Grega";
    private static final String descriptionTest = "Molho de tomate, mussarela, presunto picado, cebola, ovos e orégano.";
    private static final Double priceTest = 25.00;

    public static Flavor createFlavor() {
        Flavor entity = new Flavor();
        entity.setName(nameTest);
        entity.setDescription(descriptionTest);
        entity.setPrice(priceTest);
        return entity;
    }

    public static Flavor createFlavorWithId() {
        return new Flavor(idTest, nameTest, descriptionTest, priceTest);
    }

    public static FlavorDto createFlavorDto() {
        return new FlavorDto(idTest, nameTest, descriptionTest, priceTest);
    }
}
