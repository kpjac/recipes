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

    public Image() {
    }

    public Image(byte[] image) {
        this.image = image;
    }

    public Image(byte[] image, String filename, String type, Recipe recipe) {
        this.image = image;
        this.filename = filename;
        this.type = type;
        this.recipe = recipe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
