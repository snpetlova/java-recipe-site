package com.bonappetit.controller;

import com.bonappetit.model.dtos.CreateRecipeDto;
import com.bonappetit.service.CategoryService;
import com.bonappetit.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final CategoryService categoryService;
    private final RecipeService recipeService;

    public RecipeController(CategoryService categoryService, RecipeService recipeService) {
        this.categoryService = categoryService;
        this.recipeService = recipeService;
    }

    @ModelAttribute(name = "createRecipeDto")
    public CreateRecipeDto createRecipeDto() {
        return new CreateRecipeDto();
    }

    @GetMapping("/add")
    public String addRecipe(Model model) {
        if(!model.containsAttribute("allCategories")) {
            List<String> allCategories = categoryService.getAllCategories();
            model.addAttribute("allCategories",allCategories);
        }
        return "recipe-add";
    }

    @PostMapping("/add")
    public String createRecipe(@Valid CreateRecipeDto createRecipeDto, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("createRecipeDto", createRecipeDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.createRecipeDto",
                    bindingResult);

            return "redirect:/recipes/add";
        }

        recipeService.saveRecipe(createRecipeDto);

        return "redirect:/home";
    }

    @GetMapping("/addToFav/{name}")
    public String addToFav(@PathVariable String name) {
        recipeService.addToFav(name);
        return "redirect:/home";
    }

    @GetMapping("/removeFromFav/{name}")
    public String removeFromFav(@PathVariable String name) {
        recipeService.removeFromFav(name);
        return "redirect:/home";
    }

}

