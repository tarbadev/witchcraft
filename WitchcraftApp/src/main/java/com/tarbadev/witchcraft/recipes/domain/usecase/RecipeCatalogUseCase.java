package com.tarbadev.witchcraft.recipes.domain.usecase;

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe;
import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeCatalogUseCase {
    private final RecipeRepository recipeRepository;

    public RecipeCatalogUseCase(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> execute() {
        return recipeRepository.findAll();
    }
}
