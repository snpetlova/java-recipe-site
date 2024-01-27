package com.bonappetit.init;

import com.bonappetit.model.entity.CategoryEntity;
import com.bonappetit.model.enums.CategoryEnum;
import com.bonappetit.repo.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBinit implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public DBinit(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        

        if (categoryRepository.count() <= 0) {
            CategoryEntity MAIN_DISH = new CategoryEntity();
            MAIN_DISH.setName(CategoryEnum.MAIN_DISH);
            MAIN_DISH.setDescription("Heart of the meal, substantial and satisfying; main dish delights taste buds.");
            categoryRepository.save(MAIN_DISH);

            CategoryEntity DESSERT = new CategoryEntity();
            DESSERT.setName(CategoryEnum.DESSERT);
            DESSERT.setDescription("Sweet finale, indulgent and delightful; dessert crowns the dining experience with joy.");
            categoryRepository.save(DESSERT);

            CategoryEntity COCKTAIL = new CategoryEntity();
            COCKTAIL.setName(CategoryEnum.COCKTAIL);
            COCKTAIL.setDescription("Sip of sophistication, cocktails blend flavors, creating a spirited symphony in every glass.");
            categoryRepository.save(COCKTAIL);
        }
    }
}


