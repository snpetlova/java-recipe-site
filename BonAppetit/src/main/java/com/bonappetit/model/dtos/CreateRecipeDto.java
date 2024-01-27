package com.bonappetit.model.dtos;

import com.bonappetit.model.enums.CategoryEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateRecipeDto {
    @NotNull
    @Size(min = 2, max = 40, message = "Recipe name should be between 2 and 40 characters!")
    private String name;

    @NotNull
    @Size(min = 2, max = 150, message = "Ingredients should be between 2 and 150 characters!")
    private String ingredients;

    @NotNull(message = "Please choose a category!")
    private CategoryEnum category;

    public CreateRecipeDto() {
    }

    public String getName() {
        return name;
    }

    public CreateRecipeDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getIngredients() {
        return ingredients;
    }

    public CreateRecipeDto setIngredients(String ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public CreateRecipeDto setCategory(CategoryEnum category) {
        this.category = category;
        return this;
    }
}
