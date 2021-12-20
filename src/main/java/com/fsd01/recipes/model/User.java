package com.fsd01.recipes.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Email
    private String email;

    @Size(min = 6, max = 100,
            message = "Password must be between 6 and 100 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*$",
            message = "Password must contain a lowercase letter, an uppercase letter, a digit, and a special character")
    @Transient
    private String passwordEntry;

    @Transient
    private String passwordRepeat;

    private String password;

    @CreationTimestamp
    private Timestamp createTime;

    @Size(min = 1, max = 50)
    @Pattern(regexp = "^[A-Za-z0-9\s]+$",
            message = "Display may only contain letters, digits and spaces")
    private String displayName;

    @Size(min = 10, max = 2000, message = "Bio must be at least 10 characters and no more than 2000 characters long.")
    private String bio;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Recipe> recipes;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<RecipeMade> recipesMade;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordEntry() {
        return passwordEntry;
    }

    public void setPasswordEntry(String passwordEntry) {
        this.passwordEntry = passwordEntry;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public Set<RecipeMade> getRecipesMade() {
        return recipesMade;
    }

    public void addRecipeMade(RecipeMade recipeMade) {
        recipesMade.add(recipeMade);
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void setRecipesMade(Set<RecipeMade> recipesMade) {
        this.recipesMade = recipesMade;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", passwordRepeat='" + passwordRepeat + '\'' +
                ", createTime=" + createTime +
                ", displayName='" + displayName + '\'' +
                ", bio='" + bio + '\'' +
                ", recipes=" + recipes +
                ", recipesMade=" + recipesMade +
                '}';
    }


}
