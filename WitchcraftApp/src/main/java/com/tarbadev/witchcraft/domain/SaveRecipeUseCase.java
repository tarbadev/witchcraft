package com.tarbadev.witchcraft.domain;

import org.springframework.stereotype.Component;

@Component
public class SaveRecipeUseCase {
  private RecipeRepository recipeRepository;

  public SaveRecipeUseCase(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  public void execute(Recipe recipe) {
    if (recipe.getId() != null)
      recipeRepository.updateRecipe(recipe);
    else
      recipeRepository.saveRecipe(recipe);
  }
}
