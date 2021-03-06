package com.fsd01.recipes.model;

import javax.measure.Quantity;
import javax.measure.quantity.Volume;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipeId", nullable = false)
    private Recipe recipe;

    private Double amount;

    @Enumerated(EnumType.ORDINAL)
    private IngredientUnit unit;

    @Transient
    private Quantity<Volume> qty;

    @NotNull
    private String name;

    public Ingredient() {
    }

    public Ingredient(Recipe recipe, Quantity qty, String name) {
        this.recipe = recipe;
        this.qty = qty;
        this.name = name;
    }

    public Ingredient(Recipe recipe, double amount, String name) {
        this.recipe = recipe;
        this.amount = amount;
        this.name = name;
    }

    public Ingredient(Recipe recipe, String name) {
        this.recipe = recipe;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Quantity getQty() {
        return qty;
    }

    public void setQty(Quantity qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public IngredientUnit getUnit() {
        return unit;
    }

    public void setUnit(IngredientUnit unit) {
        this.unit = unit;
    }
}
