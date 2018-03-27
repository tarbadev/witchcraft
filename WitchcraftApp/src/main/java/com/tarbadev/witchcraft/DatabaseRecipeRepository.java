package com.tarbadev.witchcraft;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseRecipeRepository {
    private RecipeRepository recipeRepository;

    public DatabaseRecipeRepository(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public List<Recipe> getAll() {
        return recipeRepository.findAll();
    }

    public Recipe get(Integer recipeId) {
        return recipeRepository.findById(recipeId).get();
    }
}
