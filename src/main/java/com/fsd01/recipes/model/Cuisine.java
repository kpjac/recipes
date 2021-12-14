package com.fsd01.recipes.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "cuisines")
public class Cuisine {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "cuisine", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Recipe> recipes;
}
