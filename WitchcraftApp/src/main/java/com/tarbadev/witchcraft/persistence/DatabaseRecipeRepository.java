package com.tarbadev.witchcraft.persistence;

import com.tarbadev.witchcraft.domain.Ingredient;
import com.tarbadev.witchcraft.domain.Recipe;
import org.springframework.stereotype.Component;

import java.util.Comparator;
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
    Recipe recipe = recipeRepository.findById(recipeId).get();
    recipe.getIngredients().sort(Comparator.comparing(Ingredient::getName));
    return recipe;
  }
}
