package com.tarbadev.witchcraft;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseRecipeRepository {
    private RecipeRepository recipeRepository;

    public DatabaseRecipeRepository(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe createRecipe(String recipe_url) {
        return recipeRepository.save(Recipe.builder().url(recipe_url).build());
    }

    public List<Recipe> getAll() {
        return recipeRepository.findAll();
    }
}
