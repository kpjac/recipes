package com.fsd01.recipes.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Lob
    private byte[] image;

    private String fileName;

    private String mimeType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipeId", referencedColumnName = "id")
    private Recipe recipe;

    public Image() {
    }

    public Image(byte[] image) {
        this.image = image;
    }

    public Image(String name, String type, byte[] data, Recipe recipe) {
        this.fileName = name;
        this.mimeType = type;
        this.image = data;
        this.recipe = recipe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getFilename() {
        return fileName;
    }

    public void setFilename(String filename) {
        this.fileName = filename;
    }

    public String getType() {
        return mimeType;
    }

    public void setType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
