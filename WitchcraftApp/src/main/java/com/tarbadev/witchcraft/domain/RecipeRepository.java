package com.tarbadev.witchcraft.domain;

import java.util.List;

public interface RecipeRepository {
  Recipe createRecipe(Recipe recipe);
  List<Recipe> findAll();
  Recipe findById(Integer recipeId);
}
