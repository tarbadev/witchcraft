package com.tarbadev.witchcraft.domain;

import org.springframework.stereotype.Component;

@Component
public class AddRecipeUseCase {
    private RecipeRepository recipeRepository;

    public AddRecipeUseCase(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe execute(Recipe recipe) {
        return this.recipeRepository.createRecipe(recipe);
    }
}
