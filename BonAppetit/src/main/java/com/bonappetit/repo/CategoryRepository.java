package com.bonappetit.repo;

import com.bonappetit.model.entity.CategoryEntity;
import com.bonappetit.model.entity.RecipeEntity;
import com.bonappetit.model.enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByName(CategoryEnum category);

    @Query("select c.recipes from CategoryEntity as c where c.name = :categoryName")
    List<RecipeEntity> findRecipesByName(@Param("categoryName") CategoryEnum categoryName);
}
