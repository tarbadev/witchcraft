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
        return recipeRepository.saveAndFlush(recipe);
    }

    public List<Recipe> findAll() {
        return recipeRepository.findAllByOrderByName();
    }

    public Recipe findById(Integer recipeId) {
        return recipeRepository.findById(recipeId).get();
    }
}
