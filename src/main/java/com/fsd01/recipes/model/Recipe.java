package com.fsd01.recipes.model;


import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creatorId", nullable = false)
    private User creator;

    private Category category;

    private Cuisine cuisine;

    private String title;

    private String description;

    @CreationTimestamp
    private Timestamp createTime;

    private Integer cookingTime;

    private Integer serves;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Ingredient> ingredients;

    @Lob
    private LinkedList<String> steps;

    private Difficulty difficulty;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<RecipeMade> timesMade;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imageId", referencedColumnName = "id")
    private Image image;


    public Recipe() {
    }

    public Recipe(User creator, Category category, Cuisine cuisine, String title, String description, Integer cookingTime, Integer serves, Set<Ingredient> ingredients, LinkedList<String> steps, Difficulty difficulty, Image image) {
        this.creator = creator;
        this.category = category;
        this.cuisine = cuisine;
        this.title = title;
        this.description = description;
        this.cookingTime = cookingTime;
        this.serves = serves;
        this.ingredients = ingredients;
        this.steps = steps;
        this.difficulty = difficulty;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Cuisine getCuisine() {
        return cuisine;
    }

    public void setCuisine(Cuisine cuisine) {
        this.cuisine = cuisine;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(Integer cookingTime) {
        this.cookingTime = cookingTime;
    }

    public Integer getServes() {
        return serves;
    }

    public void setServes(Integer serves) {
        this.serves = serves;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public LinkedList<String> getSteps() {
        return steps;
    }

    public void setSteps(LinkedList<String> steps) {
        this.steps = steps;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Set<RecipeMade> getTimesMade() {
        return timesMade;
    }

    public void addTimeMade(RecipeMade timeMade) {
        timesMade.add(timeMade);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", creator=" + creator +
                ", category=" + category +
                ", cuisine=" + cuisine +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", cookingTime=" + cookingTime +
                ", serves=" + serves +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                ", difficulty=" + difficulty +
                ", timesMade=" + timesMade +
                ", image=" + image +
                '}';
    }
}
