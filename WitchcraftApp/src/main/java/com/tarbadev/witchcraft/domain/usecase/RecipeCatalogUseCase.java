package com.tarbadev.witchcraft.domain.usecase;

import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.repository.RecipeRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeCatalogUseCase {
    private RecipeRepository recipeRepository;

    public RecipeCatalogUseCase(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> execute() {
        return recipeRepository.findAll();
    }
}
