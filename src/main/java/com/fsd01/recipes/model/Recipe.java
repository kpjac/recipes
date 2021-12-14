package com.fsd01.recipes.model;


import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.sql.Timestamp;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cuisineId", nullable = false)
    private Cuisine cuisine;

    private String title;

    private String description;

    @CreationTimestamp
    private Timestamp createTime;









}
