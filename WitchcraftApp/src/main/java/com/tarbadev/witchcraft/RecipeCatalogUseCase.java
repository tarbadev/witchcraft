package com.tarbadev.witchcraft;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeCatalogUseCase {
    private DatabaseRecipeRepository databaseRecipeRepository;

    public RecipeCatalogUseCase(DatabaseRecipeRepository databaseRecipeRepository) {
        this.databaseRecipeRepository = databaseRecipeRepository;
    }

    public List<Recipe> execute() {
        return databaseRecipeRepository.getAll();
    }
}
