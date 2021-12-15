package com.fsd01.recipes.model;

import javax.measure.Quantity;
import javax.persistence.*;

@Entity
@Table(name = "ingredients")
public class Ingredient<T extends Quantity<T>> {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipeId", nullable = false)
    private Recipe recipe;

    @Lob
    private Quantity<T> qty;

    private String name;









}
