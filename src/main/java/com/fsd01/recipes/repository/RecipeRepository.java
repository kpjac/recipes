package com.fsd01.recipes.repository;

import com.fsd01.recipes.model.Category;
import com.fsd01.recipes.model.Cuisine;
import com.fsd01.recipes.model.Recipe;
import com.fsd01.recipes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Optional<Recipe> findRecipeById(Long id);

    // Return a list of recipe categories in order by how many recipes in the category were created or made by a user
    @Query("select r.category from Recipe r left join RecipeMade rm on r.id = rm.recipe.id WHERE r.creator.id = ?1 OR rm.user.id = ?1 group by r.category order by count(r.id) desc")
    List<Category> countByCategoryByUser(Long id);

    // Return a list of recipe cuisines in order by how many recipes in the cuisine were created or made by a user
    @Query("select r.cuisine from Recipe r left join RecipeMade rm on r.id = rm.recipe.id WHERE r.creator.id = ?1 OR rm.user.id = ?1 group by r.cuisine having count(r.id) > 0 order by count(r.id) desc")
    List<Cuisine> countByCuisineByUser(Long id);

    // The above queries supply the following one.
    // Return a list of recipes of preferred category or any of preferred cuisines
    @Query("select r from Recipe r where r.category = ?1 or r.cuisine in ?2 order by r.createTime desc")
    List<Recipe> findRecommendedRecipes(Category category, List<Cuisine> cuisines);

    List<Recipe> findAllByCreator(User user);

    List<Recipe> findAllByCategory(Category category);

    // Top 10 new
    List<Recipe> findTop8ByOrderByCreateTimeDesc();

    // Top 10 popular
    List<Recipe> findTop8ByOrderByLikesDesc();

    // Top 10 category (by popularity)
    List<Recipe> findTop8ByCategoryOrderByLikes(Category category);

    // Keyword search
    @Query("select r from Recipe r where lower(concat(r.title, ' ', r.description)) like %?1%")
    List<Recipe> findAllByKeyword(String keyword);





}
