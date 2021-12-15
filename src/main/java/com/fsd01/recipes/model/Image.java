package com.fsd01.recipes.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private byte[] image;

    private String filename;

    private String type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipeId", referencedColumnName = "id")
    private Recipe recipe;


}
