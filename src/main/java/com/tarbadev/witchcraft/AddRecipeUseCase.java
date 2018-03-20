package com.tarbadev.witchcraft;

import org.springframework.stereotype.Component;

@Component
public class AddRecipeUseCase {
    private RecipeRepository recipeRepository;

    public AddRecipeUseCase(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe execute(String url) {
        return this.recipeRepository.createRecipe(url);
    }
}
