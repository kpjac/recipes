package com.fsd01.recipes.repository;

import com.fsd01.recipes.model.RecipeMade;
import com.fsd01.recipes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeMadeRepository extends JpaRepository<RecipeMade, Long> {

    @Query("select rm from RecipeMade rm where rm.recipe.id=:recipeId and rm.user.id =:userId")
    Optional<RecipeMade> findByRecipeAndUser(@Param("recipeId")Long recipeId, @Param("userId")Long userId);

    List<RecipeMade> findRecipeMadesByUser(User user);
}
