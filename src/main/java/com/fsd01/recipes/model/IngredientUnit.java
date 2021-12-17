package com.fsd01.recipes.model;

import lombok.Getter;

public enum IngredientUnit {

    NONE("None"),
    TEASPOON("Tsp"),
    TABLESPOON("Tbsp"),
    CUP("Cups"),
    FLUID_OUNCE("Fl. oz"),
    PINT("Pints"),
    QUART("Quarts"),
    LITRE("Litres"),
    MILLILITRE("ml");

    @Getter
    String label;

    IngredientUnit(String label) {
        this.label = label;
    }
}
