package com.tarbadev.witchcraft.domain;

import com.tarbadev.witchcraft.persistence.DatabaseRecipeRepository;
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
