package com.tarbadev.witchcraft;

import org.springframework.stereotype.Component;

@Component
public class AddRecipeUseCase {
    private DatabaseRecipeRepository databaseRecipeRepository;

    public AddRecipeUseCase(DatabaseRecipeRepository databaseRecipeRepository) {
        this.databaseRecipeRepository = databaseRecipeRepository;
    }

    public Recipe execute(String url) {
        return this.databaseRecipeRepository.createRecipe(url);
    }
}
