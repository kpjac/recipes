package com.fsd01.recipes.model;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creatorId")
    private User creator;

    @Enumerated(EnumType.ORDINAL)
    private Category category;

    @Enumerated(EnumType.ORDINAL)
    private Cuisine cuisine;

    private String title;

    @Size(max=25000)
    private String description;

    @CreationTimestamp
    private Timestamp createTime;

    private Integer cookingTime;

    private Integer serves;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;

    @ElementCollection
    @Column(length=25000)
    private List<String> steps;

    @Enumerated(EnumType.ORDINAL)
    private Difficulty difficulty;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<RecipeMade> timesMade;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Image> images;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;

    private Integer likes;


    public Recipe() {
        likes = 0;
    }

    public Recipe(User creator, Category category, Cuisine cuisine, String title, String description, Integer cookingTime, Integer serves, List<Ingredient> ingredients, List<String> steps, Difficulty difficulty) {
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
        this.likes = 0;
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public void addStep(String step) {
        steps.add(step);
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void addImage(Image image) {
        images.add(image);
    }

    public void setTimesMade(Set<RecipeMade> timesMade) {
        this.timesMade = timesMade;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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
                ", image=" + images +
                '}';
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void like() {
        likes++;
    }
}
