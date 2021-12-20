package com.fsd01.recipes.controller;

import com.fsd01.recipes.model.*;
import com.fsd01.recipes.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public void saveRecipe(Recipe recipe) {
        recipeRepo.save(recipe);
    }

    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepo.findRecipeById(id);
    }

    public Category getPreferredCategory(User user){
        List<Category> cats = recipeRepo.countByCategoryByUser(user.getId());
        if (cats == null || cats.size() == 0) {
            return null;
        } else
            return cats.get(0);
    }

    public List<Cuisine> getTop3Cuisines(User user) {
        List<Cuisine> cuisines = recipeRepo.countByCuisineByUser(user.getId());
        if (cuisines == null) {
            return new ArrayList<>();
        }
        if (cuisines.size() < 3) {
            return cuisines;
        }
        return cuisines.subList(0,2);
    }

    public List<Recipe> getRecommendedRecipes(Category cat, List<Cuisine> cuisines) {
        return recipeRepo.findRecommendedRecipes(cat, cuisines);
    }

    public List<Recipe> getRecipeSearchResults(String keyword) {
        return recipeRepo.findAllByKeyword(keyword);
    }

    public List<Recipe> getRecipeCategory(Category category) {
        return recipeRepo.findAllByCategory(category);
    }

    public RecipeRepository getRecipeRepo() {
        return recipeRepo;
    }
}
