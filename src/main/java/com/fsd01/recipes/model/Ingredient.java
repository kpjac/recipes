package com.fsd01.recipes.model;

import javax.measure.Quantity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ingredients")
public class Ingredient<T extends Quantity<T>> {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipeId", nullable = false)
    @NotNull
    private Recipe recipe;

    @Lob
    private Quantity<T> qty;

    @NotNull
    private String name;

    public Ingredient() {
    }

    public Ingredient(Recipe recipe, Quantity<T> qty, String name) {
        this.recipe = recipe;
        this.qty = qty;
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

    public Quantity<T> getQty() {
        return qty;
    }

    public void setQty(Quantity<T> qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
