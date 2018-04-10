package com.tarbadev.witchcraft.domain;

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
