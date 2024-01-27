package com.bonappetit.controller;

import com.bonappetit.model.entity.RecipeEntity;
import com.bonappetit.model.enums.CategoryEnum;
import com.bonappetit.service.RecipeService;
import com.bonappetit.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private final UserService userService;
    private final RecipeService recipeService;

    public HomeController(UserService userService, RecipeService recipeService) {
        this.userService = userService;
        this.recipeService = recipeService;
    }

    @GetMapping("/")
    public String indexPage(Model model, Authentication authentication) {
        // User is not logged in, show index page content
        return "index";
    }


    @GetMapping("/home")
    public String homePage(Model model, Authentication authentication) {
        // User is logged in, show home page content
        model.addAttribute("username", userService.getCurrentUser().getUsername());
        Map<CategoryEnum, List<RecipeEntity>> recipesByCategories = recipeService.getRecipesByCategories();
        model.addAttribute("desserts", recipesByCategories.get(CategoryEnum.DESSERT));
        model.addAttribute("cocktails", recipesByCategories.get(CategoryEnum.COCKTAIL));
        model.addAttribute("mainDishes", recipesByCategories.get(CategoryEnum.MAIN_DISH));
        model.addAttribute("favourites", userService.getAllFavourites());

        return "home";

    }


}
