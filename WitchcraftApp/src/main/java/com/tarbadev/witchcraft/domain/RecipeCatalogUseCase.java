package com.tarbadev.witchcraft.domain;

import com.tarbadev.witchcraft.persistence.DatabaseRecipeRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeCatalogUseCase {
    private DatabaseRecipeRepository databaseRecipeRepository;

    public RecipeCatalogUseCase(DatabaseRecipeRepository databaseRecipeRepository) {
        this.databaseRecipeRepository = databaseRecipeRepository;
    }

    public List<Recipe> execute() {
        return databaseRecipeRepository.findAll();
    }
}
