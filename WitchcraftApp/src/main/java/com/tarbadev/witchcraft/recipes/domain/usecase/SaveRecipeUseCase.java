package com.tarbadev.witchcraft.recipes.domain.usecase;

import com.tarbadev.witchcraft.recipes.domain.entity.Recipe;
import com.tarbadev.witchcraft.recipes.domain.repository.RecipeRepository;
import org.springframework.stereotype.Component;

@Component
public class SaveRecipeUseCase {
  private final RecipeRepository recipeRepository;

  public SaveRecipeUseCase(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  public Recipe execute(Recipe recipe) {
    if (recipe.getId() != null && recipe.getId() > 0)
      recipe = recipeRepository.updateRecipe(recipe);
    else
      recipe = recipeRepository.saveRecipe(recipe);

    return recipe;
  }
}
