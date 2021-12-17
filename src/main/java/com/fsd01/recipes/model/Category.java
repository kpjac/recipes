package com.fsd01.recipes.model;

import lombok.Getter;

public enum Category {

    BREAKFAST("Breakfast"),
    LUNCH("Lunch"),
    DINNER("Dinner"),
    APPETIZER("Appetizer"),
    SALAD("Salad"),
    MAIN("Main"),
    SIDE("Side"),
    BAKED("Baked"),
    DESSERT("Dessert"),
    SNACK("Snack"),
    SOUP("Soup"),
    HOLIDAY("Holiday"),
    BEVERAGE("Beverage");

    @Getter
    String label;

    Category(String label) {
        this.label = label;
    }

}
