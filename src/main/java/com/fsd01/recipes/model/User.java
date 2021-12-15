package com.fsd01.recipes.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @Transient
    private String passwordRepeat;

    @CreationTimestamp
    private Timestamp createTime;

    @Size(min = 1, max = 50)
    private String displayName;

    @Size(min = 10, max = 2000, message = "Bio must be at least 10 characters and no more than 2000 characters long.")
    private String bio;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Recipe> recipes;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<RecipeMade> recipesMade;





}
