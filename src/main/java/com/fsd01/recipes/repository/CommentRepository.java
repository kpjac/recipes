package com.fsd01.recipes.repository;


import com.fsd01.recipes.model.Comment;
import com.fsd01.recipes.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findCommentById(Long id);

    List<Comment> findByRecipeOrderByTimestampDesc(Recipe recipe);


}
