package com.tarbadev.witchcraft.domain.usecase;

import com.tarbadev.witchcraft.domain.entity.Recipe;
import com.tarbadev.witchcraft.domain.repository.RecipeRepository;
import org.springframework.stereotype.Component;

@Component
public class SaveRecipeUseCase {
  private RecipeRepository recipeRepository;

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
