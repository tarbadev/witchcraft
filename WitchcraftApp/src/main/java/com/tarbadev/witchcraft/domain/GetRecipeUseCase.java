package com.tarbadev.witchcraft.domain;

import com.tarbadev.witchcraft.persistence.DatabaseRecipeRepository;
import org.springframework.stereotype.Component;

@Component
public class GetRecipeUseCase {
    private DatabaseRecipeRepository databaseRecipeRepository;

    public GetRecipeUseCase(DatabaseRecipeRepository databaseRecipeRepository) {
        this.databaseRecipeRepository = databaseRecipeRepository;
    }

    public Recipe execute(Integer recipeId) {
        return databaseRecipeRepository.findById(recipeId);
    }
}
