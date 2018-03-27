package com.tarbadev.witchcraft;

import org.springframework.stereotype.Component;

@Component
public class AddRecipeUseCase {
    private DatabaseRecipeRepository databaseRecipeRepository;

    public AddRecipeUseCase(DatabaseRecipeRepository databaseRecipeRepository) {
        this.databaseRecipeRepository = databaseRecipeRepository;
    }

    public Recipe execute(Recipe recipe) {
        return this.databaseRecipeRepository.createRecipe(recipe);
    }
}
