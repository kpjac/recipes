package com.fsd01.recipes.controller;

import com.fsd01.recipes.model.Recipe;
import com.fsd01.recipes.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepo;

    @Autowired
    public RecipeService(RecipeRepository recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    public RecipeRepository getUserRepo() {
        return recipeRepo;
    }

    public void addRecipe(Recipe recipe) {
        recipeRepo.save(recipe);
    }

}
