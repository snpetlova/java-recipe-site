package com.bonappetit.service;

import com.bonappetit.model.dtos.CreateRecipeDto;
import com.bonappetit.model.entity.CategoryEntity;
import com.bonappetit.model.entity.RecipeEntity;
import com.bonappetit.model.entity.UserEntity;
import com.bonappetit.model.enums.CategoryEnum;
import com.bonappetit.repo.CategoryRepository;
import com.bonappetit.repo.RecipeRepository;
import com.bonappetit.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public RecipeService(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UserService userService, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public void saveRecipe(CreateRecipeDto createRecipeDto) {

        //Create current recipe
        RecipeEntity currRecipe = new RecipeEntity();

        //Add name and ingredients
        currRecipe.setName(createRecipeDto.getName());
        currRecipe.setIngredients(createRecipeDto.getIngredients());

        //Use categoryRepository to get category from the database
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findByName(createRecipeDto.getCategory());
        CategoryEntity categoryEntity = optionalCategoryEntity.get();
        //Add category to recipe
        currRecipe.setCategory(categoryEntity);

        //Get currently logged-in user
        String username = userService.getCurrentUser().getUsername();
        UserEntity userEntity = userRepository.findByUsername(username).get();

        //Add recipe owner to recipe
        currRecipe.setAddedBy(userEntity);

        //Save recipe to the database
        recipeRepository.save(currRecipe);

    }


    //Get recipes by categories
    public Map<CategoryEnum, List<RecipeEntity>> getRecipesByCategories() {
        Map<CategoryEnum, List<RecipeEntity>> map = new HashMap<>();

        List<CategoryEntity> allCategories = categoryRepository.findAll();
        for (CategoryEntity currCategory : allCategories) {
            CategoryEnum categoryName = currCategory.getName();
            List<RecipeEntity> recipesByName = categoryRepository.findRecipesByName(categoryName);
            map.put(categoryName,recipesByName);

        }


        return map;

    }

    public void addToFav(String name) {
        Optional<RecipeEntity> optionalRecipe = recipeRepository.findByName(name);
        RecipeEntity currRecipe = optionalRecipe.get();

        String username = userService.getCurrentUser().getUsername();
        Optional<UserEntity> byUsername = userRepository.findByUsername(username);
        UserEntity userEntity = byUsername.get();

        userEntity.getFavouriteRecipeEntity().add(currRecipe);
        userRepository.save(userEntity);
    }

@Transactional
    public void removeFromFav(String name) {
        Optional<RecipeEntity> optionalRecipe = recipeRepository.findByName(name);
        RecipeEntity currRecipe = optionalRecipe.get();

        String username = userService.getCurrentUser().getUsername();
        Optional<UserEntity> byUsername = userRepository.findByUsername(username);
        UserEntity userEntity = byUsername.get();

        userEntity.getFavouriteRecipeEntity().remove(currRecipe);
        userRepository.save(userEntity);
    }
}
